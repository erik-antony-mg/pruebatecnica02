package com.amg.pruebatecnica02.application.controllers;

import com.amg.pruebatecnica02.application.dto.AuthRequestDto;
import com.amg.pruebatecnica02.application.dto.AuthResponseDto;
import com.amg.pruebatecnica02.application.services.impl.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login (@RequestBody @Validated AuthRequestDto authRequestDto){

        return ResponseEntity.ok(authenticationService.login(authRequestDto));
    }
}
