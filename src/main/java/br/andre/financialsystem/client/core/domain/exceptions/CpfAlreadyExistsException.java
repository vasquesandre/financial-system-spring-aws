package br.andre.financialsystem.client.core.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CpfAlreadyExistsException extends RuntimeException {
    public CpfAlreadyExistsException() {
        super("CPF already exists");
    }
}
