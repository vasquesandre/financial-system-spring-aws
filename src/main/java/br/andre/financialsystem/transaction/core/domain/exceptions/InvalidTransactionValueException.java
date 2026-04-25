package br.andre.financialsystem.transaction.core.domain.exceptions;

public class InvalidTransactionValueException extends RuntimeException {
    public InvalidTransactionValueException() {
        super("invalid transaction value");
    }
}
