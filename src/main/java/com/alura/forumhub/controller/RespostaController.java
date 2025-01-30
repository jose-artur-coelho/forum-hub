package com.alura.forumhub.controller;

import com.alura.forumhub.dto.resposta.CriarRespostaDTO;
import com.alura.forumhub.dto.resposta.ResponseRespostaExtendedDTO;
import com.alura.forumhub.dto.resposta.ResponseRespostaShortDTO;
import com.alura.forumhub.dto.shared.AtualizarTopicoOuRespostaDTO;
import com.alura.forumhub.service.RespostaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/respostas")
@RequiredArgsConstructor
public class RespostaController {
    private final RespostaService service;

    @PostMapping
    public ResponseEntity<ResponseRespostaShortDTO> criarResposta(@RequestBody @Valid CriarRespostaDTO dto) {
        ResponseRespostaShortDTO response = service.criar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResponseRespostaShortDTO>> listarRespostas(){
        List<ResponseRespostaShortDTO> respostas = service.listar();

        return ResponseEntity.ok().body(respostas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseRespostaExtendedDTO> recuperarResposta(@PathVariable Long id) {
        ResponseRespostaExtendedDTO resposta = service.recuperar(id);
        return ResponseEntity.ok().body(resposta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseRespostaShortDTO> atualizarResposta(@PathVariable long id, @RequestBody AtualizarTopicoOuRespostaDTO dto) {
        ResponseRespostaShortDTO response = service.atualizarResposta(id, dto);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> apagarResposta(@PathVariable Long id) {
        service.apagar(id);

        return ResponseEntity.noContent().build();

    }
}