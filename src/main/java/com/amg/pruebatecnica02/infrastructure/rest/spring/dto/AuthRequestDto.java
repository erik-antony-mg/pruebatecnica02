package com.amg.pruebatecnica02.infrastructure.rest.spring.dto;

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
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,}$",
            message = "La contraseña debe tener al menos una letra mayúscula, una letra minúscula, un dígito, un carácter especial y una longitud mínima de 4 caracteres.")
    private String password;
}
