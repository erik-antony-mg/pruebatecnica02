package com.amg.pruebatecnica02.infrastructure.persistence.repository;

import com.amg.pruebatecnica02.domain.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles,Long> {
}
