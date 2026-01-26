package br.andre.financialsystem.domain.exception.security;

public class InvalidAuthenticationException extends RuntimeException {
    public InvalidAuthenticationException() {
        super("Authenticated user has no role");
    }
}