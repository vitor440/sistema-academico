package com.sistema_escolar.sistema.escolar.repository;

import com.sistema_escolar.sistema.escolar.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByClientId(String clientId);
}
