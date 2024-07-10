package com.amg.pruebatecnica02.infrastructure.persistence.repository;

import com.amg.pruebatecnica02.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> , JpaSpecificationExecutor<Usuario> {
    Optional<Usuario> findUsuarioByEmail(String email);

}
