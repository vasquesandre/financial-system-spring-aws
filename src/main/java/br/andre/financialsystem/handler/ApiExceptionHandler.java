package br.andre.financialsystem.handler;

import br.andre.financialsystem.domain.exception.client.ClientNotFoundException;
import br.andre.financialsystem.domain.exception.client.CpfAlreadyExistsException;
import br.andre.financialsystem.domain.exception.client.InvalidCpfException;
import br.andre.financialsystem.dto.handler.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ApiError> handle(ClientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("CLIENT_NOT_FOUND", "Client not found"));
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<ApiError> handle(CpfAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiError("CPF_ALREADY_EXISTS", "CPF already exists"));
    }

    @ExceptionHandler(InvalidCpfException.class)
    public ResponseEntity<ApiError> handle(InvalidCpfException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("INVALID_CPF", "CPF is invalid"));
    }
}
