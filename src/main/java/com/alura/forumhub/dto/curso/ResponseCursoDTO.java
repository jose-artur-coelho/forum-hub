package com.alura.forumhub.dto.curso;


import com.alura.forumhub.domain.curso.Curso;

public record ResponseCursoDTO(
        Long id,
        String nome
) {
    public ResponseCursoDTO(Curso curso) {
        this(curso.getId(), curso.getTitulo());
    }
}
