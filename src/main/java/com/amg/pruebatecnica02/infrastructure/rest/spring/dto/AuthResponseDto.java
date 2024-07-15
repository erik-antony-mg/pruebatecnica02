package com.amg.pruebatecnica02.infrastructure.rest.spring.dto;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String mensaje;
    private String token;
}
