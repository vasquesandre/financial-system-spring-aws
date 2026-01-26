package br.andre.financialsystem.dto.client;

import br.andre.financialsystem.domain.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateClientRequest {
    @NotBlank private String name;
    @NotBlank private String cpf;
    @NotBlank @Size(min = 6, message = "password must have at least 6 characters") private String password;
    @NotBlank @Email private String email;
    @NotNull private Role role;

    public CreateClientRequest(String name, String cpf, String password, String email, Role role) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }
}
