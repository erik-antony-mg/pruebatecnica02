package com.amg.pruebatecnica02.application.services.impl;

import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.ResponseDto;
import com.amg.pruebatecnica02.data.DataTest;
import com.amg.pruebatecnica02.domain.entity.Usuario;
import com.amg.pruebatecnica02.domain.exceptions.UsuarioNotFountException;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.RolesRepository;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.UsuarioRepository;
import com.amg.pruebatecnica02.infrastructure.persistence.specification.SearchUsuarioSpecification;
import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UsuarioServiceImplTest {

    @Mock
    UsuarioRepository usuarioRepository;
    @Mock
    RolesRepository rolesRepository;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    JwtUtils jwtUtils;
    @InjectMocks
    UsuarioServiceImpl usuarioService;

    @Test
    void testCreateUsuario_returnResponseDto() {

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(DataTest.oneUser());
        when(passwordEncoder.encode(any(String.class))).thenReturn("123");
        when(rolesRepository.findById(anyLong())).thenReturn(DataTest.oneRole());
        when(jwtUtils.generateToken(anyString())).thenReturn("token");

        ResponseDto responseDto = usuarioService.createUsuario(DataTest.requestDto());

        assertTrue(responseDto.isActive());
        assertEquals(DataTest.oneUser().getFechaCreacion(),responseDto.fecha_creacion());
        assertEquals("token",responseDto.token());

        verify(usuarioRepository).save(any(Usuario.class));
    }
    @Test
    void testCreateUsuario_whenRolesEqualNull_returnResponseDto() {

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(DataTest.oneUserWithRolNull());
        when(passwordEncoder.encode(any(String.class))).thenReturn("123");
        when(rolesRepository.findById(anyLong())).thenReturn(DataTest.oneRole());
        when(jwtUtils.generateToken(anyString())).thenReturn("token");

        ResponseDto responseDto = usuarioService.createUsuario(DataTest.requestDtoAdmin());

        assertTrue(responseDto.isActive());
        assertEquals(DataTest.oneUser().getFechaCreacion(),responseDto.fecha_creacion());
        assertEquals("token",responseDto.token());

        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testListUsuario_returnListResponseDto() {

        when(usuarioRepository.findAll(any(SearchUsuarioSpecification.class))).thenReturn(DataTest.usuarioList());

        List<ResponseDto> responseDtoList=usuarioService.listarUsuario(null,null,null,null,"true");

        assertEquals(2,responseDtoList.size());
        verify(usuarioRepository).findAll(any(SearchUsuarioSpecification.class));

    }

    @Test
    void testDeleteUser_ReturnResponseDto() {
        when(usuarioRepository.findUsuarioByEmail(anyString())).thenReturn(Optional.ofNullable(DataTest.oneUser()));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(DataTest.oneUserNoActivate());
        ResponseDto responseDto=usuarioService.deleteUser(DataTest.oneUser().getEmail());

        assertFalse(responseDto.isActive());
        verify(usuarioRepository).findUsuarioByEmail(anyString());
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void testDeleteUser_ThrowUsuarioNotFound_ReturnResponseDto() {

        when(usuarioRepository.findUsuarioByEmail(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(DataTest.oneUserNoActivate());

        assertThrows(UsuarioNotFountException.class,()-> usuarioService.deleteUser(anyString()));

        verify(usuarioRepository).findUsuarioByEmail(anyString());
        verify(usuarioRepository,never()).save(any(Usuario.class));
    }
}