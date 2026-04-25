package br.andre.financialsystem.client.core.domain.exceptions;

public class InvalidOrNullPasswordException extends RuntimeException {
    public InvalidOrNullPasswordException() {
        super("invalid or null password");
    }
}
