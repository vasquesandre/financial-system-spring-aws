package br.andre.financialsystem.repository.client;

import br.andre.financialsystem.client.core.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Optional<Client> findByCpf(String cpf);

}
