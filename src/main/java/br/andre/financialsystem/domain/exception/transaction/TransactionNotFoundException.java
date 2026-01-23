package br.andre.financialsystem.domain.exception.transaction;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("transaction not found");
    }
}
