package br.andre.financialsystem.service;

import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.exception.transaction.InvalidTransactionValueException;
import br.andre.financialsystem.domain.exception.transaction.TransactionClientIdIsNullException;
import br.andre.financialsystem.domain.exception.transaction.TransactionNotFoundException;
import br.andre.financialsystem.domain.model.Transaction;
import br.andre.financialsystem.dto.transaction.CreateTransactionRequest;
import br.andre.financialsystem.repository.transaction.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.repository = transactionRepository;
    }

    public Transaction create(CreateTransactionRequest request) {
        if(request.getValue().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidTransactionValueException();
        }

        if(request.getClientId() == null) {
            throw new TransactionClientIdIsNullException();
        }

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                request.getClientId(),
                request.getValue(),
                TransactionStatus.PENDING,
                Instant.now()
        );

        return repository.save(transaction);
    }

    public Transaction findById(String id) {
        return repository.findById(id)
                .orElseThrow(TransactionNotFoundException::new);
    }

    public List<Transaction> findByClientId(String id) {
        return repository.findByClientId(id);
    }

}
