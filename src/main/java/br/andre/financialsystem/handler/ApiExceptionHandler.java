package br.andre.financialsystem.handler;

import br.andre.financialsystem.domain.exception.auth.UnauthorizedLoginException;
import br.andre.financialsystem.domain.exception.client.*;
import br.andre.financialsystem.domain.exception.transaction.InvalidTransactionValueException;
import br.andre.financialsystem.domain.exception.transaction.TransactionNotFoundException;
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

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiError> handle(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError("INSUFFICIENT_BALANCE", "Insufficient balance"));
    }

    @ExceptionHandler(ClientNotActiveException.class)
    public ResponseEntity<ApiError> handle(ClientNotActiveException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError("CLIENT_NOT_ACTIVE", "Client not active"));
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiError> handle(TransactionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiError("TRANSACTION_NOT_FOUND", "Transaction not found"));
    }

    @ExceptionHandler(InvalidTransactionValueException.class)
    public ResponseEntity<ApiError> handle(InvalidTransactionValueException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiError("INVALID_TRANSACTION_VALUE", "Invalid transaction value"));
    }

    @ExceptionHandler(UnauthorizedLoginException.class)
    public ResponseEntity<ApiError> handle(UnauthorizedLoginException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiError("UNAUTHORIZED LOGIN", "Unauthorized login"));
    }
}
