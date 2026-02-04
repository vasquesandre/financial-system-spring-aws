package br.andre.financialsystem.service;

import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.domain.enums.Role;
import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.exception.auth.AccessDeniedException;
import br.andre.financialsystem.domain.exception.client.ClientNotActiveException;
import br.andre.financialsystem.domain.exception.client.InsufficientBalanceException;
import br.andre.financialsystem.domain.exception.transaction.InvalidTransactionValueException;
import br.andre.financialsystem.domain.exception.transaction.TransactionNotFoundException;
import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.domain.model.Transaction;
import br.andre.financialsystem.dto.transaction.CreateTransactionRequest;
import br.andre.financialsystem.repository.client.ClientRepository;
import br.andre.financialsystem.repository.transaction.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public TransactionService(TransactionRepository transactionRepository, ClientService clientService, ClientRepository clientRepository) {
        this.repository = transactionRepository;
        this.clientService = clientService;
        this.clientRepository = clientRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(TransactionService.class);

    public Transaction create(CreateTransactionRequest request, String clientId) {

        Client client = clientService.findById(clientId);

        if(client.getStatus() != ClientStatus.ACTIVE) {
            throw new ClientNotActiveException();
        }

        if(request.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionValueException();
        }

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                clientId,
                request.getValue(),
                request.getType(),
                client.getBalance(),
                TransactionStatus.PENDING,
                Instant.now()
        );

        try {
            switch (request.getType()) {
                case CREDIT -> client.credit(request.getValue());
                case DEBIT -> client.debit(request.getValue());
            }

            transaction.complete();
            clientRepository.save(client);
            log.info(
                    "Transaction {} completed for clientId={} with value={}",
                    transaction.getId(),
                    client.getId(),
                    request.getValue()
            );
        } catch (InsufficientBalanceException e) {
            transaction.fail();
            repository.save(transaction);
            log.warn(
                    "Transaction failed due to insufficient balance. clientId={}, value={}",
                    client.getId(),
                    request.getValue()
            );
            throw e;
        }

        return repository.save(transaction);
    }

    public Transaction findById(String id) {
        return repository.findById(id)
                .orElseThrow(TransactionNotFoundException::new);
    }

    public List<Transaction> findByClientId(String targetId, String requesterId, Role requesterRole) {
        if(requesterRole != Role.ADMIN && !requesterId.equals(targetId)) {
            log.warn("ACCESS_DENIED: clientId={} trying to get transactions from clientId={}", requesterId, targetId);
            throw new AccessDeniedException();
        }
        return repository.findTransactionByClientId(targetId);
    }

}
