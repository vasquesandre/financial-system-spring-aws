package br.andre.financialsystem.domain.model;

import br.andre.financialsystem.domain.enums.TransactionStatus;
import br.andre.financialsystem.domain.enums.TransactionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private String id;
    private String clientId;
    private BigDecimal value;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    private BigDecimal balanceAtCreation;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    public Transaction(String id, String clientId, BigDecimal value, TransactionType type, BigDecimal balanceAtCreation, TransactionStatus status, Instant createdAt) {
        this.id = id;
        this.clientId = clientId;
        this.value = value;
        this.type = type;
        this.balanceAtCreation = balanceAtCreation;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Transaction() {
    }

    public void complete() {
        setStatus(TransactionStatus.COMPLETED);
        setUpdatedAt(Instant.now());
    }

    public void fail() {
        setStatus(TransactionStatus.FAILED);
        setUpdatedAt(Instant.now());
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

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getBalanceAtCreation() {
        return balanceAtCreation;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
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
