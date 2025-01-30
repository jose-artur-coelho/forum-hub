package com.alura.forumhub.service;

import com.alura.forumhub.domain.resposta.Resposta;
import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.dto.resposta.CriarRespostaDTO;
import com.alura.forumhub.dto.resposta.ResponseRespostaExtendedDTO;
import com.alura.forumhub.dto.resposta.ResponseRespostaShortDTO;
import com.alura.forumhub.dto.shared.AtualizarTopicoOuRespostaDTO;
import com.alura.forumhub.exception.resposta.RespostaNaoExisteException;
import com.alura.forumhub.exception.topico.TopicoNaoExisteException;
import com.alura.forumhub.repository.RespostaRepository;
import com.alura.forumhub.repository.TopicoRepository;
import com.alura.forumhub.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespostaService {
    private final RespostaRepository respostaRepository;
    private final TopicoRepository topicoRepository;


    public ResponseRespostaShortDTO criar(CriarRespostaDTO dto) {
        Usuario autor = SecurityUtil.getUsuarioDaRequisicao();
        Topico topico = topicoRepository.findById(dto.topicoId()).orElseThrow(() -> new TopicoNaoExisteException(dto.topicoId()));
        Resposta resposta = respostaRepository.save(new Resposta(dto, autor, topico));
        return new ResponseRespostaShortDTO(resposta);
    }

    public List<ResponseRespostaShortDTO> listar() {
        return respostaRepository.findAll().stream().map(ResponseRespostaShortDTO::new).toList();
    }

    public void apagar(Long id) {
        respostaRepository.deleteById(id);
    }

    public ResponseRespostaShortDTO atualizarResposta(long id, AtualizarTopicoOuRespostaDTO dto) {
        Resposta respostaParaAtualizar = respostaRepository.findById(id).orElseThrow(() -> new RespostaNaoExisteException(id));

        respostaParaAtualizar.setTitulo(
                dto.titulo() == null || dto.titulo().isEmpty() ?
                respostaParaAtualizar.getTitulo() :
                dto.titulo());

        respostaParaAtualizar.setMensagem(
                dto.mensagem() == null || dto.mensagem().isEmpty() ?
                respostaParaAtualizar.getMensagem() :
                dto.mensagem());

        respostaRepository.save(respostaParaAtualizar);

        return new ResponseRespostaShortDTO(respostaParaAtualizar);
    }

    public ResponseRespostaExtendedDTO recuperar(Long id) {
        Resposta resposta = respostaRepository.findById(id).orElseThrow(() -> new RespostaNaoExisteException(id));

        return new ResponseRespostaExtendedDTO(resposta);
    }
}
