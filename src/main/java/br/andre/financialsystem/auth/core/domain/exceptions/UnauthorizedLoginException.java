package br.andre.financialsystem.auth.core.domain.exceptions;

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("unauthorized login");
    }
}
