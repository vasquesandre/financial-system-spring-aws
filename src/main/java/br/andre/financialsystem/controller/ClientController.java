package br.andre.financialsystem.controller;

import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.dto.client.ClientResponse;
import br.andre.financialsystem.dto.client.CreateClientRequest;
import br.andre.financialsystem.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    public ClientController(ClientService clientService) {
        this.service = clientService;
    }

    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    @PostMapping
    public ResponseEntity<ClientResponse> save(@RequestBody @Valid CreateClientRequest request) {
        log.info("POST_CLIENT_REQUEST creating client with cpf={}", request.getCpf());
        Client client = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ClientResponse(client));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> findById(@PathVariable String id) {
        log.info("GET_CLIENT_RESPONSE finding client with id={}", id);
        Client client = service.findById(id);
        return ResponseEntity.ok(new ClientResponse(client));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<ClientResponse> findByCpf(@PathVariable String cpf) {
        log.info("GET_CLIENT_RESPONSE finding client with cpf={}", cpf);
        Client client = service.findByCpf(cpf);
        return ResponseEntity.ok(new ClientResponse(client));
    }
}
