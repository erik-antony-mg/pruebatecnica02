package com.amg.pruebatecnica02.infrastructure.security;

import com.amg.pruebatecnica02.domain.entity.Usuario;
import com.amg.pruebatecnica02.infrastructure.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario=usuarioRepository.findUsuarioByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("usuario con el "+email+" no encontrado"));

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList=new ArrayList<>();
        usuario.getRoles()
                .forEach(rol -> simpleGrantedAuthorityList
                        .add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleName().name()))));
        return new User(usuario.getEmail(),
                usuario.getPassword(),
                usuario.getIsActive(),
                true,
                true,
                true,
                simpleGrantedAuthorityList);
    }
}
