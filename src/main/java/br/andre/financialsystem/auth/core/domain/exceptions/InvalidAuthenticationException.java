package br.andre.financialsystem.auth.core.domain.exceptions;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("Authenticated user has no role");
    }
}