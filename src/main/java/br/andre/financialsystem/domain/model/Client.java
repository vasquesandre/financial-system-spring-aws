package br.andre.financialsystem.domain.model;

import br.andre.financialsystem.domain.enums.ClientStatus;

import java.time.Instant;

public class Client {
    private final String id;
    private final String name;
    private final String cpf;
    private final String email;
    private ClientStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public Client(String id, String name, String cpf, String email, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.createdAt = createdAt;
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
