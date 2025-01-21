package com.alura.forumhub.dto.usuario;

import com.alura.forumhub.domain.usuario.Usuario;

public record ResponseUsuarioDTO(
        Long id,
        String nome,
        String email

) {
    public ResponseUsuarioDTO(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
