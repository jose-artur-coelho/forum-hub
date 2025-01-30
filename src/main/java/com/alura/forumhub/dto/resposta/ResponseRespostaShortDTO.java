package com.alura.forumhub.dto.resposta;

import com.alura.forumhub.domain.resposta.Resposta;

public record ResponseRespostaShortDTO(
        Long id,
        String titulo,
        String mensagem,
        String autor
) {
    public  ResponseRespostaShortDTO(Resposta resposta){
        this(resposta.getId(), resposta.getTitulo(), resposta.getMensagem(), resposta.getAutor().getNome());
    }

}
