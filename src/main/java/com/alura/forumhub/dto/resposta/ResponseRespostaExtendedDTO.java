package com.alura.forumhub.dto.resposta;

import com.alura.forumhub.domain.resposta.Resposta;
import com.alura.forumhub.dto.topico.ResponseTopicoShortDTO;

import java.time.LocalDateTime;

public record ResponseRespostaExtendedDTO(
        ResponseTopicoShortDTO topico,
        Long id,
        String titulo,
        String mensagem,
        LocalDateTime data,
        String autor

) {
    public ResponseRespostaExtendedDTO(Resposta resposta) {
        this(new ResponseTopicoShortDTO(resposta.getTopico()),
                resposta.getId(),
                resposta.getTitulo(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getAutor().getNome()
        );

    }
}
