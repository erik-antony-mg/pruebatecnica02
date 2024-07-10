package com.amg.pruebatecnica02.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;


import java.util.List;

public record RequestDto(
       @NotBlank(message = "el campo nombre no debe estar vacio !!")
       String name,
       @NotBlank(message = "el campo email no debe estar vacio !!")
       @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$" ,message = "debe tener el formato example@exp.com")
       String email,
       @NotBlank (message = "el campo password no debe estar vacio !!")
       @Pattern(
               regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{4,}$",
               message = "La contraseña debe tener al menos una letra mayúscula, una letra minúscula, un dígito, un carácter especial y una longitud mínima de 4 caracteres.")
       String password,
        Boolean isActive,
        @Valid @NotEmpty(message = "el campo phones no debe estar vacio") List<PhoneDto> phones
) {
    public record PhoneDto(
          @NotBlank(message = "el campo numero no debe estar vacio !!")
          @Pattern(regexp = "^9\\d{8}$",message = "El numero debe tener 9 caracteres numericos y debe comenzar con el numero 9") String number,
          @NotBlank(message = "el campo codigo de ciudad no debe estar vacio !!")
          @Pattern(regexp = "^\\d+$",message = "el campo codigo ciudad solo acepta numeros y mayores a cero") String citycode,
          @NotBlank(message = "el campo codigo de pais no debe estar vacio !!")
          @Pattern(regexp = "^\\d+$",message = "el campo codigo pais solo acepta numeros y mayores a cero") String countrycode) {}
}
