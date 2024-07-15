package com.amg.pruebatecnica02.application.services.impl;

import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.RequestDto;
import com.amg.pruebatecnica02.infrastructure.rest.spring.dto.ResponseDto;
import com.amg.pruebatecnica02.application.services.UsuarioService;
import com.amg.pruebatecnica02.domain.entity.Phone;
import com.amg.pruebatecnica02.domain.entity.Roles;
import com.amg.pruebatecnica02.domain.entity.Usuario;
import com.amg.pruebatecnica02.domain.exceptions.UsuarioNotFountException;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.RolesRepository;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.UsuarioRepository;
import com.amg.pruebatecnica02.infrastructure.persistence.specification.SearchUsuarioSpecification;
import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final JwtUtils jwtUtils;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public ResponseDto createUsuario(RequestDto requestDto) {

            Usuario newUsuario= new Usuario();
            Set<Roles> roles= new HashSet<>();
            List<Phone> phoneList= requestDto.phones().stream()
                    .map(phoneDto-> Phone.builder()
                            .number(phoneDto.number())
                            .cityCode(phoneDto.citycode())
                            .contryCode(phoneDto.countrycode())
                            .usuario(newUsuario)
                            .build()).toList();
            newUsuario.setName(requestDto.name());
            newUsuario.setEmail(requestDto.email());
            newUsuario.setPassword(passwordEncoder.encode(requestDto.password()));
            newUsuario.setPhones(phoneList);

            if (!newUsuario.getEmail().contains("admin")){
                    Roles role= rolesRepository.findById(2L).get();
                    roles.add(role);
                    newUsuario.setRoles(roles);
            }
            if (newUsuario.getEmail().contains("admin")){
                Roles role= rolesRepository.findById(1L).get();
                roles.add(role);
                newUsuario.setRoles(roles);
            }

            Usuario usuario= usuarioRepository.save(newUsuario);
            return new ResponseDto(
                    usuario.getUsuarioId(),
                    usuario.getFechaCreacion(),
                    usuario.getFechaModificacion(),
                    usuario.getLastLogin(),
                    jwtUtils.generateToken(usuario.getEmail()),
                    usuario.getIsActive()
            );
    }


    @Override
    public List<ResponseDto> listarUsuario(String email, String numeroPhone, LocalDate fechaDesde, LocalDate fechaHasta, String isActive) {
        boolean active=Boolean.parseBoolean(isActive);
        SearchUsuarioSpecification specification=new SearchUsuarioSpecification(email,numeroPhone,fechaDesde,fechaHasta,active);

        List<Usuario> usuarioList= usuarioRepository.findAll(specification);

        return usuarioList.stream().map(usuario -> new ResponseDto(usuario.getUsuarioId(),
                usuario.getFechaCreacion(),
                usuario.getFechaModificacion(),
                usuario.getLastLogin(),
                jwtUtils.generateToken(usuario.getEmail()),
                usuario.getIsActive())).toList();
    }

    @Override
    @Transactional
    public ResponseDto deleteUser(String email) {
        Usuario usuario = usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(()-> new UsuarioNotFountException("usuario no encontrado"));
        usuario.setIsActive(false);
        usuarioRepository.save(usuario);

        return new ResponseDto(
                usuario.getUsuarioId(),
                usuario.getFechaCreacion(),
                usuario.getFechaModificacion(),
                usuario.getLastLogin(),
                jwtUtils.generateToken(usuario.getEmail()),
                usuario.getIsActive()
        );
    }
}
