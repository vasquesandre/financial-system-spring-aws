package br.andre.financialsystem.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateClientRequest {
    @NotBlank private String name;
    @NotBlank private String cpf;
    @NotBlank @Size(min = 6, message = "password must have at least 6 characters") private String password;
    @NotBlank @Email private String email;

    public CreateClientRequest(String name, String cpf, String password, String email) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
