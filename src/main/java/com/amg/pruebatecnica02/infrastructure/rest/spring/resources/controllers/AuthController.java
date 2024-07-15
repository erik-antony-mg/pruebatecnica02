package com.amg.pruebatecnica02.infrastructure.rest.spring.resources.controllers;

import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.AuthRequestDto;
import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.AuthResponseDto;
import com.amg.pruebatecnica02.application.services.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Tag(name = "Authenticaction" , description = "Controllador para la Authentificacion")
@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(
            summary = "Login",
            description = "Metodo para iniciar sesion",
            tags = {"Authenticaction"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "un dto de tipo AuthRequestDto que contiene usuario y contraseña",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthRequestDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "inicio de session logrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponseDto.class)
                            )
                    )
            }
//            el name debe coincidir con el la configuracion de seguridad swagerconfig
//            ,
//            security = {@SecurityRequirement(name = "Security Token")}
    )
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody @Validated AuthRequestDto authRequestDto){

        return ResponseEntity.ok(authenticationService.login(authRequestDto));
    }
}
