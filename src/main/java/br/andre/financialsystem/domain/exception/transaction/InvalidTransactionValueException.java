package br.andre.financialsystem.domain.exception.transaction;

public class InvalidTransactionValueException extends RuntimeException {
    public InvalidTransactionValueException() {
        super("invalid transaction value");
    }
}
