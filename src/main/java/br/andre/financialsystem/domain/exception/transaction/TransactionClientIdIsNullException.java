package br.andre.financialsystem.domain.exception.transaction;

public class TransactionClientIdIsNullException extends RuntimeException {
    public TransactionClientIdIsNullException() {
        super("transaction client id is null");
    }
}
