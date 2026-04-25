package br.andre.financialsystem.client.core.domain.exceptions;

public class ClientNotActiveException extends RuntimeException {
    public ClientNotActiveException() {
        super("client not active");
    }
}
