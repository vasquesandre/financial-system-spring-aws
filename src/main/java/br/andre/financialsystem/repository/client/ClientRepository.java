package br.andre.financialsystem.repository.client;

import br.andre.financialsystem.domain.model.Client;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(String id);

    Optional<Client> findByCpf(String cpf);

}
