package br.andre.financialsystem.domain.exception.client;

public class ClientNotActiveException extends RuntimeException {
    public ClientNotActiveException() {
        super("client not active");
    }
}
