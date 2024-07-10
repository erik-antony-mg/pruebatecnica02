package com.amg.pruebatecnica02.application.services;

import com.amg.pruebatecnica02.application.dto.RequestDto;
import com.amg.pruebatecnica02.application.dto.ResponseDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface UsuarioService {
    ResponseDto createUsuario(RequestDto requestDto);

    List<ResponseDto> listarUsuario(String email, String numeroPhone, LocalDate fechaDesde, LocalDate fechaHasta, String isActive);

    ResponseDto deleteUser(String email);
}
