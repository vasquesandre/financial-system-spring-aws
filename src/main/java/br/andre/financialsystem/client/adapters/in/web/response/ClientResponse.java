package br.andre.financialsystem.client.adapters.in.web.response;

import br.andre.financialsystem.client.core.domain.enums.ClientStatus;
import br.andre.financialsystem.client.core.domain.Client;

import java.math.BigDecimal;

public class ClientResponse {
    private final String id;
    private final String name;
    private final String email;
    private final BigDecimal balance;
    private final ClientStatus status;

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
        this.balance = client.getBalance();
        this.status = client.getStatus();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public ClientStatus getStatus() {
        return status;
    }
}
