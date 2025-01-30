package com.alura.forumhub.dto.resposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CriarRespostaDTO(
        @NotNull(message = "O id do tópico é obrigatório.")
        Long topicoId,
        @NotBlank(message = "O título da resposta é obrigatório.")
        String titulo,
        @NotBlank(message = "A mensagem da resposta é obrigatória.")
        String mensagem


) {
}
