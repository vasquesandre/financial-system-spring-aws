package br.andre.financialsystem.service;

import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.enums.TransactionType;
import br.andre.financialsystem.domain.exception.client.ClientNotActiveException;
import br.andre.financialsystem.domain.exception.client.InsufficientBalanceException;
import br.andre.financialsystem.domain.exception.transaction.InvalidTransactionValueException;
import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.domain.model.Transaction;
import br.andre.financialsystem.dto.transaction.CreateTransactionRequest;
import br.andre.financialsystem.repository.client.ClientRepository;
import br.andre.financialsystem.repository.transaction.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private TransactionService transactionService;

    private Client activeClient;

    @BeforeEach
    void setup() {
        activeClient = new Client(
                "client-id",
                "Andre",
                "11122233300",
                "andre@email.com",
                new BigDecimal("500.00"),
                ClientStatus.ACTIVE,
                Instant.now()
        );
    }

    @Test
    void shouldCreateCreditTransactionSuccessfully() {
        CreateTransactionRequest request = new CreateTransactionRequest(
                activeClient.getId(),
                new BigDecimal("100.00"),
                TransactionType.CREDIT
        );

        when(clientService.findById(activeClient.getId()))
                .thenReturn(activeClient);

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.create(request);

        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
        assertEquals(new BigDecimal("600.00"), activeClient.getBalance());

        verify(clientRepository).save(activeClient);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void shouldCreateDebitTransactionSuccessfully() {
        CreateTransactionRequest request = new CreateTransactionRequest(
                activeClient.getId(),
                new BigDecimal("200.00"),
                TransactionType.DEBIT
        );

        when(clientService.findById(activeClient.getId()))
                .thenReturn(activeClient);

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaction transaction = transactionService.create(request);

        assertEquals(TransactionStatus.COMPLETED, transaction.getStatus());
        assertEquals(new BigDecimal("300.00"), activeClient.getBalance());

        verify(clientRepository).save(activeClient);
        verify(transactionRepository).save(transaction);
    }

    @Test
    void shouldFailWhenClientIsNotActive() {
        Client inactiveClient = new Client(
                "client-id",
                "Andre",
                "11122233300",
                "andre@email.com",
                new BigDecimal("500.00"),
                ClientStatus.INACTIVE,
                Instant.now()
        );

        CreateTransactionRequest request = new CreateTransactionRequest(
                inactiveClient.getId(),
                new BigDecimal("100.00"),
                TransactionType.DEBIT
        );

        when(clientService.findById(inactiveClient.getId()))
                .thenReturn(inactiveClient);

        assertThrows(ClientNotActiveException.class,
                () -> transactionService.create(request));

        verifyNoInteractions(transactionRepository);
        verify(clientRepository, never()).save(any());
    }

    @Test
    void shouldFailWhenTransactionValueIsZeroOrNegative() {
        CreateTransactionRequest request = new CreateTransactionRequest(
                activeClient.getId(),
                BigDecimal.ZERO,
                TransactionType.CREDIT
        );

        when(clientService.findById(activeClient.getId()))
                .thenReturn(activeClient);

        assertThrows(InvalidTransactionValueException.class,
                () -> transactionService.create(request));

        verifyNoInteractions(transactionRepository);
        verify(clientRepository, never()).save(any());
    }

    @Test
    void shouldSaveFailedTransactionWhenInsufficientBalance() {
        CreateTransactionRequest request = new CreateTransactionRequest(
                activeClient.getId(),
                new BigDecimal("1000.00"),
                TransactionType.DEBIT
        );

        when(clientService.findById(activeClient.getId()))
                .thenReturn(activeClient);

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(InsufficientBalanceException.class,
                () -> transactionService.create(request));

        verify(transactionRepository).save(argThat(
                transaction -> transaction.getStatus() == TransactionStatus.FAILED
        ));

        verify(clientRepository, never()).save(any());
    }
}