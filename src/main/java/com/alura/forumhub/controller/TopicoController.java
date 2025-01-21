package com.alura.forumhub.controller;

import com.alura.forumhub.dto.topico.AtualizarTopicoDTO;
import com.alura.forumhub.dto.topico.CriarTopicoDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoFullDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoShortDTO;
import com.alura.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoService service;

    @PostMapping
    public ResponseEntity<ResponseTopicoShortDTO> criarTopico(@RequestBody @Valid CriarTopicoDTO dto) {
        ResponseTopicoShortDTO response = service.criar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseTopicoShortDTO>> listarTopicos(){
        List<ResponseTopicoShortDTO> topicos = service.listar();

        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseTopicoFullDTO> recuperarTopico(@PathVariable Long id) {
        ResponseTopicoFullDTO topico = service.recuperar(id);

        return ResponseEntity.ok().body(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTopicoShortDTO> atualizarTopico(@PathVariable long id, @RequestBody AtualizarTopicoDTO dto) {
        ResponseTopicoShortDTO response = service.atualizarTopico(id, dto);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarTopico(@PathVariable Long id) {
        service.apagar(id);

        return ResponseEntity.noContent().build();
    }

}
