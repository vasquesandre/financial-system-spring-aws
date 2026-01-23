package br.andre.financialsystem.repository.client;

import br.andre.financialsystem.domain.model.Client;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private final Map<String, Client> storage = new ConcurrentHashMap<>();

    @Override
    public Client save(Client client) {
        storage.put(client.getId(), client);
        return client;
    }

    @Override
    public Optional<Client> findById(String id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        return storage.values()
                .stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst();
    }
}
