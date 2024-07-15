package com.amg.pruebatecnica02.infrastructure.rest.spring.resources.controllers;

import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.RequestDto;
import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.ResponseDto;
import com.amg.pruebatecnica02.application.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Usuario", description = "Controlador para gestionar usuarios")
@RestController
@RequestMapping("/api/v1/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/list")
    ResponseEntity<List<ResponseDto>> ListUser(@RequestParam(required = false) String email,
                                               @RequestParam(required = false) String numeroPhone,
                                               @RequestParam(required = false) LocalDate fechaDesde,
                                               @RequestParam(required = false) LocalDate fechaHasta,
                                               @RequestParam(required = false,defaultValue = "true") String isActive){
    if (usuarioService.listarUsuario(email,numeroPhone,fechaDesde,fechaHasta,isActive).isEmpty()){
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
        return ResponseEntity.ok(usuarioService.listarUsuario(email,numeroPhone,fechaDesde,fechaHasta,isActive));
    }

    @Operation(
            summary = "Create User",
            description = "Crear un nuevo usuario",
            tags = {"Usuario"},
            security = {@SecurityRequirement(name = "Security Token")},
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Usuario creado exitosamente"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "No autorizado"
                    )
            }
    )
    @PostMapping("/create")
    ResponseEntity<ResponseDto> createUser(@RequestBody @Validated RequestDto requestDto){
        return new ResponseEntity<>(usuarioService.createUsuario(requestDto),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{email}")
    ResponseEntity<ResponseDto>deleteUser(@PathVariable String email){
        return ResponseEntity.ok(usuarioService.deleteUser(email));
    }
}
