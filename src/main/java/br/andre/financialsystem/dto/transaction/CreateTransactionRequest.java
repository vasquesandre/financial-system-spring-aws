package br.andre.financialsystem.dto.transaction;

import br.andre.financialsystem.domain.enums.TransactionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateTransactionRequest {

    @NotBlank private final String clientId;
    @NotBlank @Positive private final BigDecimal value;
    @NotBlank private final TransactionType type;

    public CreateTransactionRequest(String clientId, BigDecimal value, TransactionType type) {
        this.clientId = clientId;
        this.value = value;
        this.type = type;
    }

    public String getClientId() {
        return clientId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public TransactionType getType() {
        return type;
    }
}
