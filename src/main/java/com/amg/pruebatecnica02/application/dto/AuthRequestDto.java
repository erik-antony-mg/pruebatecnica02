package com.amg.pruebatecnica02.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestDto {
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "debe tener el formato example@exp.com")
    @NotBlank(message = "el campo email no debe estar vacio !!")
    private String email;
    @NotBlank (message = "el campo password no debe estar vacio !!")
    private String password;
}
