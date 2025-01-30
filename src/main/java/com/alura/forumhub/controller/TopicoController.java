package com.alura.forumhub.controller;

import com.alura.forumhub.dto.shared.AtualizarTopicoOuRespostaDTO;
import com.alura.forumhub.dto.topico.CriarTopicoDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoExtendedDTO;
import com.alura.forumhub.dto.topico.ResponseTopicoShortDTO;
import com.alura.forumhub.service.TopicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/topicos")
@RequiredArgsConstructor
public class TopicoController {

    private final TopicoService service;

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
    public ResponseEntity<ResponseTopicoExtendedDTO> recuperarTopico(@PathVariable Long id) {
        ResponseTopicoExtendedDTO topico = service.recuperar(id);

        return ResponseEntity.ok().body(topico);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTopicoShortDTO> atualizarTopico(@PathVariable long id, @RequestBody AtualizarTopicoOuRespostaDTO dto) {
        ResponseTopicoShortDTO response = service.atualizarTopico(id, dto);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarTopico(@PathVariable Long id) {
        service.apagar(id);

        return ResponseEntity.noContent().build();
    }

}