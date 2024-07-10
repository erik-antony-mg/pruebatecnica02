package com.amg.pruebatecnica02.data;

import com.amg.pruebatecnica02.application.dto.RequestDto;
import com.amg.pruebatecnica02.application.dto.ResponseDto;
import com.amg.pruebatecnica02.domain.entity.Phone;
import com.amg.pruebatecnica02.domain.entity.Roles;
import com.amg.pruebatecnica02.domain.entity.Usuario;
import com.amg.pruebatecnica02.domain.entity.enums.RoleName;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataTest {



    public static Usuario oneUser(){
        Set<Roles> roles =new HashSet<>();
        roles.add(Roles.builder().rolesId(2L).roleName(RoleName.INVITADO).build());
        List<Phone> phoneList= new ArrayList<>();
        phoneList.add(Phone.builder()
                .phoneId(UUID.randomUUID())
                .number("963258741")
                .cityCode("51")
                .contryCode("54")
                .build());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .email("prueba@gmail.com")
                .name("prueba")
                .fechaCreacion(LocalDate.now())
                .fechaModificacion(LocalDate.now())
                .isActive(true)
                .lastLogin(LocalDateTime.now().format(formatter))
                .password("123")
                .roles(roles)
                .phones(phoneList)
                .build();
    }
    public static Usuario oneUserNoActivate(){
        Set<Roles> roles =new HashSet<>();
        roles.add(Roles.builder().rolesId(2L).roleName(RoleName.INVITADO).build());
        List<Phone> phoneList= new ArrayList<>();
        phoneList.add(Phone.builder()
                .phoneId(UUID.randomUUID())
                .number("963258741")
                .cityCode("51")
                .contryCode("54")
                .build());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .email("prueba@gmail.com")
                .name("prueba")
                .fechaCreacion(LocalDate.now())
                .fechaModificacion(LocalDate.now())
                .isActive(false)
                .lastLogin(LocalDateTime.now().format(formatter))
                .password("123")
                .roles(roles)
                .phones(phoneList)
                .build();
    }
    public static Usuario oneUserWithRolNull(){
        List<Phone> phoneList= new ArrayList<>();
        phoneList.add(Phone.builder()
                .phoneId(UUID.randomUUID())
                .number("963258741")
                .cityCode("51")
                .contryCode("54")
                .build());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .email("prueba.admin@gmail.com")
                .name("prueba")
                .fechaCreacion(LocalDate.now())
                .fechaModificacion(LocalDate.now())
                .isActive(true)
                .lastLogin(LocalDateTime.now().format(formatter))
                .password("123")
                .roles(null)
                .phones(phoneList)
                .build();
    }

    public static List<Usuario> usuarioList(){

        List<Usuario> usuarioList=new ArrayList<>();

        List<Phone> phoneList= new ArrayList<>();
        phoneList.add(Phone.builder()
                .phoneId(UUID.randomUUID())
                .number("963258741")
                .cityCode("51")
                .contryCode("54")
                .build());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        usuarioList.add(Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .email("prueba.admin@gmail.com")
                .name("prueba")
                .fechaCreacion(LocalDate.now())
                .fechaModificacion(LocalDate.now())
                .isActive(true)
                .lastLogin(LocalDateTime.now().format(formatter))
                .password("123")
                .roles(null)
                .phones(phoneList).build());
        usuarioList.add(Usuario.builder()
                .usuarioId(UUID.randomUUID())
                .email("prueba1@gmail.com")
                .name("prueba1")
                .fechaCreacion(LocalDate.now())
                .fechaModificacion(LocalDate.now())
                .isActive(true)
                .lastLogin(LocalDateTime.now().format(formatter))
                .password("123456")
                .roles(null)
                .phones(phoneList).build());

        return usuarioList;
    }

    public static Optional<Roles> oneRole(){
        return Optional.ofNullable(Roles.builder().rolesId(2L).roleName(RoleName.INVITADO).build());
    }


    public static RequestDto requestDto(){
        List<RequestDto.PhoneDto> phoneDtos= new ArrayList<>();
        phoneDtos.add(new RequestDto.PhoneDto("963258741","51","54"));
        return new RequestDto("prueba","prueba@gmail.com","123",null,
               phoneDtos);
    }
    public static RequestDto requestDtoAdmin(){
        List<RequestDto.PhoneDto> phoneDtos= new ArrayList<>();
        phoneDtos.add(new RequestDto.PhoneDto("963258741","51","54"));
        return new RequestDto("prueba","prueba.admin@gmail.com","123",null,
                phoneDtos);
    }

    public static List<ResponseDto> responseDtoList(){

        List<ResponseDto> responseDtoList = new ArrayList<>();
        ResponseDto responseDto= new ResponseDto( UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now(),
                "hoy",
                "token",
                true);
        ResponseDto responseDto1= new ResponseDto( UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now(),
                "hoy",
                "token",
                true);
        responseDtoList.add(responseDto);
        responseDtoList.add(responseDto1);

        return responseDtoList;
    }

    public static ResponseDto responseDto(){
        return new ResponseDto(
                UUID.randomUUID(),
                LocalDate.now(),
                LocalDate.now(),
                "2024-07-09 19:23:07",
                "accessToken",
                true
        );
    }
}
