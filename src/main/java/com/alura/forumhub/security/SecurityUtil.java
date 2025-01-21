package com.alura.forumhub.security;

import com.alura.forumhub.domain.usuario.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.stream.Collectors;

public class SecurityUtil {

    public static Usuario getUsuarioDaRequisicao() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) authentication.getPrincipal();
    }

    public Optional<String> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "NONE".describeConstable();
        }


        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList().stream().findFirst();
    }
}
