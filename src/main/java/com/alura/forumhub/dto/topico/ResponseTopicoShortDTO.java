package com.alura.forumhub.dto.topico;


public record ResponseTopicoShortDTO(
        Long id,
        String titulo,
        String mensagem,
        String curso,
        String autor
) {

}
