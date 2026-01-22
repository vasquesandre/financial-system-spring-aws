package br.andre.financialsystem.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateClientRequest {
    @NotBlank private String name;
    @NotBlank private String cpf;
    @NotBlank @Email private String email;

    public CreateClientRequest(String name, String cpf, String email) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
    }

    public CreateClientRequest() {
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
}
