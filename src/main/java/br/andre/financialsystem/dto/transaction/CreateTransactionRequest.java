package br.andre.financialsystem.dto.transaction;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateTransactionRequest {

    @NotBlank private final String clientId;
    @NotBlank @Positive private final BigDecimal value;

    public CreateTransactionRequest(String clientId, BigDecimal value) {
        this.clientId = clientId;
        this.value = value;
    }

    public String getClientId() {
        return clientId;
    }

    public BigDecimal getValue() {
        return value;
    }
}
