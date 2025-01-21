package com.alura.forumhub.service;

import com.alura.forumhub.domain.curso.Curso;
import com.alura.forumhub.dto.curso.CriarCursoDTO;
import com.alura.forumhub.dto.curso.ResponseCursoDTO;
import com.alura.forumhub.exception.curso.CursoJaExisteException;
import com.alura.forumhub.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    CursoRepository repository;


    public ResponseCursoDTO criar(CriarCursoDTO dto) {
        boolean cursoExiste = repository.existsByTitulo(dto.titulo());

        if (cursoExiste) {
            throw new CursoJaExisteException(dto.titulo());
        }

        Curso curso = repository.save(new Curso(dto));
        return new ResponseCursoDTO(curso);
    }

    public List<ResponseCursoDTO> listar() {
        List<Curso> cursos = repository.findAll();
        return cursos.stream().map(ResponseCursoDTO::new).toList();
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
