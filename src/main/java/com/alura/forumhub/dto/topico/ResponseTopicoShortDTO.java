package com.alura.forumhub.dto.topico;


import com.alura.forumhub.domain.topico.Topico;

public record ResponseTopicoShortDTO(
        Long id,
        String titulo,
        String mensagem,
        String curso,
        String autor
) {
    public ResponseTopicoShortDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getCurso().getTitulo(),
                topico.getAutor().getNome());
    }
}
