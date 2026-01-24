package br.andre.financialsystem.domain.exception.auth;

public class UnauthorizedLoginException extends RuntimeException {
    public UnauthorizedLoginException() {
        super("unauthorized login");
    }
}
