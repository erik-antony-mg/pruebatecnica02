package com.amg.pruebatecnica02.application.services.impl;

import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.AuthRequestDto;
import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.AuthResponseDto;
import com.amg.pruebatecnica02.data.DataTest;
import com.amg.pruebatecnica02.domain.entity.Usuario;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.UsuarioRepository;
import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtUtils jwtUtils;
    @InjectMocks
    private AuthenticationService authenticationService;


    @Test
    void testLogin_returnAuthResponseDto() {
        AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .email("prueba@gmail.com")
                .password("123")
                .build();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword());

        Mockito.when(authenticationManager.authenticate(authToken)).thenReturn(authToken);
        Mockito.when(usuarioRepository.findUsuarioByEmail(authRequestDto.getEmail())).thenReturn(Optional.ofNullable(DataTest.oneUser()));
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(DataTest.oneUser());
        Mockito.when(jwtUtils.generateToken(authRequestDto.getEmail())).thenReturn("token");

        AuthResponseDto authResponseDto= authenticationService.login(authRequestDto);

        assertEquals("token",authResponseDto.getToken());

        Mockito.verify(authenticationManager).authenticate(authToken);
        Mockito.verify(usuarioRepository).save(Mockito.any(Usuario.class));
        Mockito.verify(jwtUtils).generateToken(authRequestDto.getEmail());
    }

    @Test
    void testLogin_ThrowUserNotFoundException(){

        AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .email("prueba@gmail.com")
                .password("123")
                .build();

        assertThrows(UsernameNotFoundException.class,()-> authenticationService.login(authRequestDto));

    }
}