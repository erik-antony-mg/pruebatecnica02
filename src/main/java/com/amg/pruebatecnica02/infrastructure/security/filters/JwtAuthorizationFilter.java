package com.amg.pruebatecnica02.infrastructure.security.filters;


import com.amg.pruebatecnica02.infrastructure.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

//@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

//    private final JwtUtils jwtUtils;
//    private final UserDetailsService userDetailsService;
//    private final HandlerExceptionResolver resolver;
//
//    public JwtAuthorizationFilter(JwtUtils jwtUtils,
//                                  UserDetailsService userDetailsService,
//                                  @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
//        this.jwtUtils = jwtUtils;
//        this.userDetailsService = userDetailsService;
//        this.resolver = resolver;
//    }
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailsService userDetailsService;
    private final HandlerExceptionResolver resolver;

    @Autowired
    public JwtAuthorizationFilter(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        try {
            String token=getToken(request);

            if (token != null && jwtUtils.validarToken(token)) {
                String username= jwtUtils.getUsername(token);
                UserDetails userDetails=userDetailsService.loadUserByUsername(username);

                UsernamePasswordAuthenticationToken authenticationToken=
                        new UsernamePasswordAuthenticationToken(username,null,userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            filterChain.doFilter(request,response);
        }catch (Exception ex){
            resolver.resolveException(request,response,null,ex);
        }
    }

    private String getToken(@NonNull HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}