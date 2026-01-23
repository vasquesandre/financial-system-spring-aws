package br.andre.financialsystem.domain.model;

import br.andre.financialsystem.domain.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.Instant;

public class Transaction {

    private final String id;
    private final String clientId;
    private final BigDecimal value;
    private final TransactionStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Transaction(String id, String clientId, BigDecimal value, TransactionStatus status, Instant createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.value = value;
        this.status = status;
        this.createdAt = createdAt;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
