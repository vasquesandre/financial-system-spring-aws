package br.andre.financialsystem.auth.core.domain.exceptions;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("Access denied");
    }
}
