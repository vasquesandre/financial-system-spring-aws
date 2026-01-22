package br.andre.financialsystem.service;

import br.andre.financialsystem.domain.model.Client;
import br.andre.financialsystem.domain.enums.ClientStatus;
import br.andre.financialsystem.dto.client.CreateClientRequest;
import br.andre.financialsystem.repository.client.ClientRepository;
import br.andre.financialsystem.domain.exception.client.CpfAlreadyExistsException;
import br.andre.financialsystem.domain.exception.client.ClientNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClientServiceTest {

    private ClientRepository clientRepository;
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        clientRepository = Mockito.mock(ClientRepository.class);
        clientService = new ClientService(clientRepository);
    }

    @Test
    void shouldCreateClientWithActiveStatus() {
        CreateClientRequest request = new CreateClientRequest(
                "Andre",
                "12345678909",
                "andre@email.com"
        );

        when(clientRepository.findByCpf(request.getCpf()))
                .thenReturn(Optional.empty());

        when(clientRepository.save(any(Client.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Client client = clientService.create(request);

        assertNotNull(client.getId());
        assertEquals(ClientStatus.ACTIVE, client.getStatus());
        assertEquals(request.getCpf(), client.getCpf());
        assertEquals(request.getEmail(), client.getEmail());
        assertNotNull(client.getCreatedAt());

        verify(clientRepository).save(any(Client.class));
    }

    @Test
    void shouldThrowExceptionWhenCpfAlreadyExists() {
        CreateClientRequest request = new CreateClientRequest(
                "Andre",
                "12345678909",
                "andre@email.com"
        );

        when(clientRepository.findByCpf(request.getCpf()))
                .thenReturn(Optional.of(mock(Client.class)));

        assertThrows(CpfAlreadyExistsException.class,
                () -> clientService.create(request));

        verify(clientRepository, never()).save(any());
    }

    @Test
    void shouldFindClientById() {
        String clientId = UUID.randomUUID().toString();

        Client client = new Client(
                clientId,
                "Andre",
                "12345678909",
                "andre@email.com",
                ClientStatus.ACTIVE,
                Instant.now()
        );

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.of(client));

        Client result = clientService.findById(clientId);

        assertEquals(clientId, result.getId());
    }

    @Test
    void shouldThrowExceptionWhenClientNotFound() {
        String clientId = UUID.randomUUID().toString();

        when(clientRepository.findById(clientId))
                .thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class,
                () -> clientService.findById(clientId));
    }
}