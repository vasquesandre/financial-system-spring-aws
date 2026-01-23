package br.andre.financialsystem.domain.exception.client;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException() {
        super("insufficient balance");
    }
}
