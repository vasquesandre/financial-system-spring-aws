package br.andre.financialsystem.repository.transaction;

import br.andre.financialsystem.domain.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository {

    Transaction save(Transaction transaction);

    Optional<Transaction> findById(String id);

    List<Transaction> findByClientId(String clientId);

}
