package com.amg.pruebatecnica02.application.services.impl;

import com.amg.pruebatecnica02.application.dto.AuthRequestDto;
import com.amg.pruebatecnica02.application.dto.AuthResponseDto;
import com.amg.pruebatecnica02.domain.entity.Usuario;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.UsuarioRepository;
import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsuarioRepository usuarioRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public AuthResponseDto login(AuthRequestDto authRequestDto){

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword());

        authenticationManager.authenticate(authToken);

        Usuario usuario= usuarioRepository.findUsuarioByEmail(authRequestDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        usuario.setLastLogin(LocalDateTime.now().format(formatter));
        usuarioRepository.save(usuario);

        AuthResponseDto authResponseDto= new AuthResponseDto();
        authResponseDto.setMensaje("el Usuario "+authRequestDto.getEmail()+" ha iniciado con exito!");
        authResponseDto.setToken(jwtUtils.generateToken(authRequestDto.getEmail()));
        return authResponseDto;

    }
}
