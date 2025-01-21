package com.alura.forumhub.dto.topico;

import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.dto.resposta.ResponseRespostaShortDTO;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseTopicoFullDTO(
        Long id,
        String titulo,
        String mensagem,
        String curso,
        String autor,
        LocalDateTime criadoEm,
        List<ResponseRespostaShortDTO> respostas
) {
    public ResponseTopicoFullDTO(Topico topico) {
        this(topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getCurso().getTitulo(),
                topico.getAutor().getNome(),
                topico.getDataCriacao(),
                topico.getRespostas().stream().map(ResponseRespostaShortDTO::new).toList());
    }
}
