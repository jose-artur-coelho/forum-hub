package com.alura.forumhub.service;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.domain.usuario.PapelUsuario;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.dto.shared.AtualizarTopicoOuRespostaDTO;
import com.alura.forumhub.dto.topico.CriarTopicoDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoExtendedDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoShortDTO;
import com.alura.forumhub.exception.OperacaoInvalidaException;
import com.alura.forumhub.exception.curso.CursoNaoExisteException;
import com.alura.forumhub.exception.topico.TopicoNaoExisteException;
import com.alura.forumhub.repository.CursoRepository;
import com.alura.forumhub.repository.TopicoRepository;
import com.alura.forumhub.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicoService {

    private final TopicoRepository topicoRepository;
    private final CursoRepository cursoRepository;

    public ResponseTopicoShortDTO criar(CriarTopicoDTO dto) {
        Usuario usuario = SecurityUtil.getUsuarioDaRequisicao();
        Curso curso = cursoRepository.findById(dto.cursoId()).orElseThrow(() -> new CursoNaoExisteException(dto.cursoId()));

        Topico topico = topicoRepository.save(
                Topico.builder()
                .autor(usuario).curso(curso)
                .mensagem(dto.mensagem())
                .titulo(dto.titulo())
                .build());

        return new ResponseTopicoShortDTO(topico);

    }


    public List<ResponseTopicoShortDTO> listar() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream().map(ResponseTopicoShortDTO::new).toList();

    }

    public ResponseTopicoExtendedDTO recuperar(Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new TopicoNaoExisteException(id));
        return new ResponseTopicoExtendedDTO(topico);
    }


    public ResponseTopicoShortDTO atualizarTopico(Long id, AtualizarTopicoOuRespostaDTO dto) {
        Topico topicoParaAtualizar = topicoRepository.findById(id).orElseThrow(() -> new TopicoNaoExisteException(id));
        Usuario usuario = SecurityUtil.getUsuarioDaRequisicao();
        if (!topicoParaAtualizar.getAutor().equals(usuario)) {
            throw new OperacaoInvalidaException();
        }
        topicoParaAtualizar.setTitulo(dto.titulo() == null || dto.titulo().isEmpty() ? topicoParaAtualizar.getTitulo() : dto.titulo());
        topicoParaAtualizar.setMensagem(dto.mensagem() == null || dto.mensagem().isEmpty() ? topicoParaAtualizar.getMensagem() : dto.mensagem());

        topicoRepository.save(topicoParaAtualizar);

        return new ResponseTopicoShortDTO(topicoParaAtualizar);

    }




    public void apagar(Long id) {
        Usuario usuario = SecurityUtil.getUsuarioDaRequisicao();
        Topico topico = topicoRepository.findById(id).orElseThrow(() -> new TopicoNaoExisteException(id));

        if (!topico.getAutor().equals(usuario) && usuario.getPapel() != PapelUsuario.ADMIN) {
            throw new OperacaoInvalidaException();
        }

        topicoRepository.deleteById(id);
    }   
}
