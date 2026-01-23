package br.andre.financialsystem.controller;

import br.andre.financialsystem.domain.model.Transaction;
import br.andre.financialsystem.dto.transaction.CreateTransactionRequest;
import br.andre.financialsystem.dto.transaction.TransactionResponse;
import br.andre.financialsystem.service.TransactionService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService transactionService) {
        this.service = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> save(@RequestBody CreateTransactionRequest request) {
        Transaction transaction = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(@PathVariable String id) {
        Transaction transaction = service.findById(id);
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<TransactionResponse>> findByClientId(@PathVariable String id) {
        List<TransactionResponse> transactions = service.findByClientId(id)
                .stream()
                .map(TransactionResponse::new)
                .toList();
        return ResponseEntity.ok(transactions);
    }
}
