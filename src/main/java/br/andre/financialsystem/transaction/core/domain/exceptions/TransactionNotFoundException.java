package br.andre.financialsystem.transaction.core.domain.exceptions;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException() {
        super("transaction not found");
    }
}
