package br.andre.financialsystem.dto.client;

import jakarta.validation.constraints.NotBlank;

public class CreateClientRequest {
    @NotBlank private String name;
    @NotBlank private String cpf;
    @NotBlank private String email;
}
