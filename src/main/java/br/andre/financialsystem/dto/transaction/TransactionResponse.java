package br.andre.financialsystem.dto.transaction;

import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.enums.TransactionType;
import br.andre.financialsystem.domain.model.Transaction;

import java.math.BigDecimal;

public class TransactionResponse {

    private final String id;
    private final String clientId;
    private final BigDecimal value;
    private final TransactionStatus status;
    private final TransactionType type;
    private final BigDecimal balanceAtCreation;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.clientId = transaction.getClientId();
        this.value = transaction.getValue();
        this.status = transaction.getStatus();
        this.type = transaction.getType();
        this.balanceAtCreation = transaction.getBalanceAtCreation();
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

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getBalanceAtCreation() {
        return balanceAtCreation;
    }
}
