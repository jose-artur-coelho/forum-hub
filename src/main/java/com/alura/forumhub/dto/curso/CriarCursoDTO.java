package com.alura.forumhub.dto.curso;

import jakarta.validation.constraints.NotBlank;

public record CriarCursoDTO(
        @NotBlank(message = "O titulo do curso é obrigatório.")
        String titulo
) {}
