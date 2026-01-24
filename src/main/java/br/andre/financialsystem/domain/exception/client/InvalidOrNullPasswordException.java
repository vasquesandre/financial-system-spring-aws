package br.andre.financialsystem.domain.exception.client;

public class InvalidOrNullPasswordException extends RuntimeException {
    public InvalidOrNullPasswordException() {
        super("invalid or null password");
    }
}
