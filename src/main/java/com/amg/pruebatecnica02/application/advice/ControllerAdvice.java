package com.amg.pruebatecnica02.application.advice;

import com.amg.pruebatecnica02.domain.exceptions.JwtCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> sqlException(SQLIntegrityConstraintViolationException ex){
        String customErrorMessage="Valor duplicado del email revise";

        Map<String,Object> error=new HashMap<>();
        error.put("Codigo", HttpStatus.BAD_REQUEST);
        error.put("Error",customErrorMessage);
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<?> method(MethodArgumentNotValidException ex){
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            Map<String, Object> errores = new HashMap<>();
            errores.put("status", HttpStatus.BAD_REQUEST);
            for (FieldError error : fieldErrors) {
                errores.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<?> badCredentialsException(BadCredentialsException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", HttpStatus.UNAUTHORIZED.value());
            error.put("error", ex.getMessage());
            error.put("custom message", "verifique su email o contraseña ..!");
            return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
        }

        @ExceptionHandler(AccessDeniedException.class)
        public ResponseEntity<?> accessDeniedException(AccessDeniedException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", HttpStatus.FORBIDDEN);
            error.put("error", ex.getMessage());
            error.put("custom message", "acceso denegado");
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }

        @ExceptionHandler(JwtCustomException.class)
        public ResponseEntity<?> jwtCustomException(JwtCustomException ex) {
            Map<String, Object> error = new HashMap<>();
            error.put("status", HttpStatus.FORBIDDEN);
            error.put("error", ex.getMessage());
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
    }


