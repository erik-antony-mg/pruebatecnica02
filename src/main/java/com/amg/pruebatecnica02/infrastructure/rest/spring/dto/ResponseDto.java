package com.amg.pruebatecnica02.infrastructure.rest.spring.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ResponseDto(
        UUID id,
        LocalDate fecha_creacion,
        LocalDate fecha_modificacion,
        String last_login,
        String token,
        Boolean isActive
) {
}
