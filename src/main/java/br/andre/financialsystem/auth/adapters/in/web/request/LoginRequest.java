package br.andre.financialsystem.auth.adapters.in.web.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank private final String cpf;
    @NotBlank private final String password;

    public LoginRequest(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPassword() {
        return password;
    }
}
