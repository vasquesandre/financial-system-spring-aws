package br.andre.financialsystem.dto.transaction;

import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.model.Transaction;

import java.math.BigDecimal;

public class TransactionResponse {

    private final String id;
    private final String clientId;
    private final BigDecimal value;
    private final TransactionStatus status;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.clientId = transaction.getClientId();
        this.value = transaction.getValue();
        this.status = transaction.getStatus();
    }

    public String getId() {
        return id;
    }

    public String getClientId() {
        return clientId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public TransactionStatus getStatus() {
        return status;
    }
}
