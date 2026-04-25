package br.andre.financialsystem.client.core.domain.exceptions;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("insufficient balance");
    }
}
