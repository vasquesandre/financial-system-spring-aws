package br.andre.financialsystem.dto.client;

import br.andre.financialsystem.domain.enums.ClientStatus;

public class ClientResponse {
    private String id;
    private String name;
    private String email;
    private ClientStatus status;
}
