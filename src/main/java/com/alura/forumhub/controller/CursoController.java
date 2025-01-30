package com.alura.forumhub.controller;

import com.alura.forumhub.dto.curso.CriarCursoDTO;
import com.alura.forumhub.dto.curso.ResponseCursoDTO;
import com.alura.forumhub.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService service;


    @Secured("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<ResponseCursoDTO> criarCurso(@RequestBody @Valid
    CriarCursoDTO dto){
       ResponseCursoDTO response = service.criar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ResponseCursoDTO> recuperarCurso(@PathVariable Long id){
        ResponseCursoDTO curso = service.recuperar(id);
        return ResponseEntity.status(HttpStatus.OK).body(curso);
    }




    @GetMapping
    public ResponseEntity<List<ResponseCursoDTO>> listarCursos(){
        List<ResponseCursoDTO> cursos = service.listar();
        return ResponseEntity.status(HttpStatus.OK).body(cursos);
    }



    @Secured("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id){
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }






}
