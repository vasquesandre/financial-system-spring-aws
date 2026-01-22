package br.andre.financialsystem.repository.client;

import br.andre.financialsystem.domain.model.Client;

import java.util.Optional;

public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(String id);

    Optional<Client> findByCpf(String cpf);

}
