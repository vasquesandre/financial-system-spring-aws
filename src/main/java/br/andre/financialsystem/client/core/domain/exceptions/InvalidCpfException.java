package br.andre.financialsystem.client.core.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCpfException extends RuntimeException {
    public InvalidCpfException() {
        super("invalid CPF");
    }
}
