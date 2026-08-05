package com.sistema_escolar.sistema.escolar.security;

import com.sistema_escolar.sistema.escolar.model.Usuario;
import com.sistema_escolar.sistema.escolar.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            Usuario usuario = usuarioService.findByUsername(jwtAuthenticationToken.getName());

            CustomAuthentication customAuthentication = new CustomAuthentication(usuario);

            SecurityContextHolder.getContext().setAuthentication(customAuthentication);
        }

        doFilter(request, response, filterChain);
    }
}
