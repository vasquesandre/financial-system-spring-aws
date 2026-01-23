package br.andre.financialsystem.dto.client;

import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.domain.model.Client;

public class ClientResponse {
    private final String id;
    private final String name;
    private final String email;
    private final ClientStatus status;

    public ClientResponse(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getEmail();
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

    public ClientStatus getStatus() {
        return status;
    }
}
