package br.andre.financialsystem.domain.exception;

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("unauthorized login");
    }
}
