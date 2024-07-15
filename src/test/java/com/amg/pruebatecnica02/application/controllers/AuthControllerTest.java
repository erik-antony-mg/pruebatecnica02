package com.amg.pruebatecnica02.application.controllers;


import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.AuthRequestDto;
import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.AuthResponseDto;
import com.amg.pruebatecnica02.application.services.impl.AuthenticationService;
import com.amg.pruebatecnica02.infrastructure.rest.spring.resources.controllers.AuthController;
import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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


@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private JwtUtils jwtUtils;
    @MockBean
    private AuthenticationService authenticationService;


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
    public void login_returnResponseEntity() throws Exception {

        AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .email("prueba@gmail.com")
                .password("123")
                .build();
        AuthResponseDto authResponseDto= new AuthResponseDto();
        authResponseDto.setToken("token");
        authResponseDto.setMensaje("usuario logeado");

        Mockito.when(authenticationService.login(authRequestDto)).thenReturn(authResponseDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
        Mockito.verify(authenticationService).login(authRequestDto);
    }
}