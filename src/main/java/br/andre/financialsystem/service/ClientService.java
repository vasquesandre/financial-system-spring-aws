package br.andre.financialsystem.service;

import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.domain.exception.client.ClientNotFoundException;
import br.andre.financialsystem.domain.exception.client.CpfAlreadyExistsException;
import br.andre.financialsystem.domain.exception.client.InvalidCpfException;
import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.dto.client.CreateClientRequest;
import br.andre.financialsystem.repository.client.ClientRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository clientRepository) {
        this.repository = clientRepository;
    }

    private boolean isValidCpf(String cpf) {
        return cpf.length() == 11;
    }

    public Client create(CreateClientRequest request) {
        if(repository.findByCpf(request.getCpf()).isPresent()) {
            throw new CpfAlreadyExistsException();
        }

        if(!isValidCpf(request.getCpf())) {
            throw new InvalidCpfException();
        }

        Client client = new Client(
                UUID.randomUUID().toString(),
                request.getName(),
                request.getCpf(),
                request.getEmail(),
                ClientStatus.ACTIVE, // temporary, PENDING after implementing AWS
                Instant.now()
        );

        return repository.save(client);
    }

    public Client findById(String id) {
        return repository.findById(id)
                .orElseThrow(ClientNotFoundException::new);
    }

    public Client findByCpf(String cpf) {
        return repository.findByCpf(cpf)
                .orElseThrow(ClientNotFoundException::new);
    }

}
