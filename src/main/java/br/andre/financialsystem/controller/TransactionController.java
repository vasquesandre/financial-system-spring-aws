package br.andre.financialsystem.controller;

import br.andre.financialsystem.domain.model.Transaction;
import br.andre.financialsystem.dto.transaction.CreateTransactionRequest;
import br.andre.financialsystem.dto.transaction.TransactionResponse;
import br.andre.financialsystem.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService transactionService) {
        this.service = transactionService;
    }

    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @PostMapping
    public ResponseEntity<TransactionResponse> save(@RequestBody CreateTransactionRequest request) {
        log.info("POST_TRANSACTION_REQUEST for client={}", request.getClientId());
        Transaction transaction = service.create(request);
        log.info("POST_TRANSACTION_RESPONSE completed transaction id={}", transaction.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new TransactionResponse(transaction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(@PathVariable String id) {
        log.info("GET_TRANSACTION_RESPONSE by id={}", id);
        Transaction transaction = service.findById(id);
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<List<TransactionResponse>> findByClientId(@PathVariable String id) {
        log.info("GET_TRANSACTION_RESPONSE by client={}", id);
        List<TransactionResponse> transactions = service.findByClientId(id)
                .stream()
                .map(TransactionResponse::new)
                .toList();
        return ResponseEntity.ok(transactions);
    }
}
