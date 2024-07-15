package com.amg.pruebatecnica02.application.controllers;

import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.RequestDto;
import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.ResponseDto;
import com.amg.pruebatecnica02.application.services.UsuarioService;
import com.amg.pruebatecnica02.data.DataTest;
import com.amg.pruebatecnica02.infrastructure.rest.spring.resources.controllers.UsuarioController;
import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;



@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private UsuarioService usuarioService;

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth->auth
                            .anyRequest().permitAll());
            return http.build();
        }
    }

    @Test
    void testListarUsuarios_returnListResponseDtoNotContent() throws Exception {

        when(usuarioService.listarUsuario(anyString(), anyString(), any(), any(), anyString()))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/usuarios/list")
                        .param("email", "test@example.com")
                        .param("numeroPhone", "123456789")
                        .param("fechaDesde", "2023-01-01")
                        .param("fechaHasta", "2023-12-31")
                        .param("isActive", "true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
    @Test
     void testListarUsuario_returnReturnListResponseDto() throws Exception {


        List<ResponseDto> usuarios = DataTest.responseDtoList();
        when(usuarioService.listarUsuario(anyString(),anyString(),any(),any(),anyString()))
                .thenReturn(usuarios);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/usuarios/list")
                        .param("email", "test@example.com")
                        .param("numeroPhone", "123456789")
                        .param("fechaDesde", "2023-01-01")
                        .param("fechaHasta", "2023-12-31")
                        .param("isActive", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].token").value("token"));
    }

    @Test
    void testCreateUsuario_returnResponseDto() throws Exception {
        RequestDto requestDto = new RequestDto(
                "prueba",
                "test@example.com",
                "Password1!",null,
                List.of(new RequestDto.PhoneDto("912345678", "01", "51"))
        );

        ResponseDto responseDto = new ResponseDto(
                UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now(),
                "2024-07-09 19:23:07",
                "accessToken",
                true
        );
        when(usuarioService.createUsuario(requestDto)).thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/usuarios/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("accessToken"));
    }

    @Test
    void testDeleteUsuario_returnResponseDto() throws Exception {
        String email="prueba@gmail.com";


        when(usuarioService.deleteUser(email)).thenReturn(DataTest.responseDto());

        mockMvc.perform((MockMvcRequestBuilders.delete("/api/v1/usuarios/delete/{email}",email)
                .contentType(MediaType.APPLICATION_JSON)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("accessToken"));

    }
}