package com.alura.forumhub.service;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.domain.topico.Topico;
import com.alura.forumhub.domain.usuario.PapelUsuario;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.dto.topico.AtualizarTopicoDTO;
import com.alura.forumhub.dto.topico.CriarTopicoDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoFullDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoShortDTO;
import com.alura.forumhub.exception.OperacaoInvalidaException;
import com.alura.forumhub.exception.curso.CursoNaoExisteException;
import com.alura.forumhub.exception.topico.TopicoNaoExisteException;
import com.alura.forumhub.repository.CursoRepository;
import com.alura.forumhub.repository.TopicoRepository;
import com.alura.forumhub.repository.UsuarioRepository;
import com.alura.forumhub.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicoService {
    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    UsuarioRepository  usuarioRepository;

    @Autowired
    CursoRepository cursoRepository;

    public ResponseTopicoShortDTO criar(CriarTopicoDTO dto) {
        Usuario usuario = SecurityUtil.getUsuarioDaRequisicao();
        Curso curso = cursoRepository.findById(dto.cursoId()).orElseThrow(CursoNaoExisteException::new);

        Topico topico = topicoRepository.save(
                Topico.builder()
                .autor(usuario).curso(curso)
                .dataCriacao(LocalDateTime.now())
                .mensagem(dto.mensagem())
                .titulo(dto.titulo())
                .build());

        return responseTopicoDTOFactory(topico, curso, usuario);

    }


    public List<ResponseTopicoShortDTO> listar() {
        List<Topico> topicos = topicoRepository.findAll();
        return topicos.stream().map(topico -> {
            Optional<Curso> curso = cursoRepository.findById(topico.getCurso().getId());
            Optional<Usuario> usuario = usuarioRepository.findById(topico.getAutor().getId());
            return responseTopicoDTOFactory(topico, curso.get(), usuario.get());
                }
        ).toList();

    }

    public ResponseTopicoFullDTO recuperar(Long id) {
        Topico topico = topicoRepository.findById(id).orElseThrow(TopicoNaoExisteException::new);
        return new ResponseTopicoFullDTO(topico);
    }


    public ResponseTopicoShortDTO atualizarTopico(Long id, AtualizarTopicoDTO dto) {
        Topico topicoParaAtualizar = topicoRepository.findById(id).orElseThrow(TopicoNaoExisteException::new);
        Usuario usuario = SecurityUtil.getUsuarioDaRequisicao();
        if (!topicoParaAtualizar.getAutor().equals(usuario)) {
            throw new OperacaoInvalidaException();
        }
        topicoParaAtualizar.setTitulo(dto.titulo() == null || dto.titulo().isEmpty() ? topicoParaAtualizar.getTitulo() : dto.titulo());
        topicoParaAtualizar.setMensagem(dto.mensagem() == null || dto.mensagem().isEmpty() ? topicoParaAtualizar.getMensagem() : dto.mensagem());

        Topico topicoAtualizado = topicoRepository.save(topicoParaAtualizar);

        return responseTopicoDTOFactory(topicoAtualizado, topicoAtualizado.getCurso(), topicoAtualizado.getAutor());

    }

    private ResponseTopicoShortDTO responseTopicoDTOFactory(Topico topico, Curso curso, Usuario autor){
        return new ResponseTopicoShortDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                curso.getTitulo(),
                autor.getNome());
    }


    public void apagar(Long id) {
        Usuario usuario = SecurityUtil.getUsuarioDaRequisicao();
        Topico topico = topicoRepository.findById(id).orElseThrow(TopicoNaoExisteException::new);

        if (!topico.getAutor().equals(usuario) && usuario.getPapel() != PapelUsuario.ADMIN) {
            throw new OperacaoInvalidaException();
        }

        topicoRepository.deleteById(id);
    }
}
