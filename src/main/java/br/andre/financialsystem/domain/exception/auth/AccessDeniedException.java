package br.andre.financialsystem.domain.exception.auth;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied");
    }
}
