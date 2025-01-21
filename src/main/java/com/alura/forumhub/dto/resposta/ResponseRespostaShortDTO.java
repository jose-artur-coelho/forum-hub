package com.alura.forumhub.dto.resposta;

import com.alura.forumhub.domain.resposta.Resposta;

public record ResponseRespostaShortDTO(
        String titulo,
        String mensagem,
        String autor
) {
    public  ResponseRespostaShortDTO(Resposta resposta){
        this(resposta.getTitulo(), resposta.getMensagem(), resposta.getAutor().getNome());
    }

}
