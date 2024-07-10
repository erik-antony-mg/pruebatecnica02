package com.amg.pruebatecnica02.domain.exceptions;


import io.jsonwebtoken.JwtException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JwtCustomException extends JwtException {

    private HttpStatus estado;
    private String mensaje;

    public JwtCustomException(HttpStatus estado, String mensaje) {
        super(mensaje);
        this.estado = estado;
        this.mensaje = mensaje;
    }
}
