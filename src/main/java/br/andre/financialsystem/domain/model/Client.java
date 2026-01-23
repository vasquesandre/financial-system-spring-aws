package br.andre.financialsystem.domain.model;

import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.domain.exception.client.InsufficientBalanceException;

import java.math.BigDecimal;
import java.time.Instant;

public class Client {
    private final String id;
    private final String name;
    private final String cpf;
    private final String email;
    private BigDecimal balance;
    private ClientStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Client(String id, String name, String cpf, String email, BigDecimal balance, ClientStatus status, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.balance = balance;
        this.status = status;
        this.createdAt = createdAt;
    }

    public void debit(BigDecimal amount) {
        if (balance.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus status) {
        this.status = status;
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
