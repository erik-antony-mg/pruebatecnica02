package com.amg.pruebatecnica02.application.controllers;

import com.amg.pruebatecnica02.application.dto.RequestDto;
import com.amg.pruebatecnica02.application.dto.ResponseDto;
import com.amg.pruebatecnica02.application.services.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    @PostMapping("/create")
    ResponseEntity<ResponseDto> createUser(@RequestBody @Validated RequestDto requestDto){
        return new ResponseEntity<>(usuarioService.createUsuario(requestDto),HttpStatus.CREATED);
    }
    @DeleteMapping("/delete/{email}")
    ResponseEntity<ResponseDto>deleteUser(@PathVariable String email){
        return ResponseEntity.ok(usuarioService.deleteUser(email));
    }

}
