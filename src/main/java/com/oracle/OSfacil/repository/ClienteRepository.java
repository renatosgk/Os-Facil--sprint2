package com.oracle.OSfacil.repository;

import com.oracle.OSfacil.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.veiculos WHERE c.id = :id")
    Optional<Cliente> findByIdWithVeiculos(@Param("id") Long id);

    @Query("SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.veiculos")
    List<Cliente> findAllWithVeiculos();

    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);
    Optional<Cliente> findByEmailIgnoreCase(String email);

}
