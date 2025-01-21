package com.alura.forumhub.dto.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarTopicoDTO(
        @NotNull(message = "O id do curso precisa ser informado.")
        Long cursoId,

        @NotBlank(message = "o titulo do tópico é obrigatório")
        String titulo,

        @NotBlank(message = "A mensagem do tópico é obrigatória.")
        String mensagem


) {


}
