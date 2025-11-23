package com.example.trabalho_coti.repository;

import com.example.trabalho_coti.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    @Query("SELECT cl FROM Cliente cl ORDER BY cl.nome")
    List<Cliente> getAllOrdenado();

    @Query("SELECT cl FROM Cliente cl WHERE cl.id = :id")
    Cliente getPorId(UUID id);

    Boolean existsByCpf(String cpf);
}
