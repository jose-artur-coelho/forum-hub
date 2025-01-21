package com.alura.forumhub.controller;

import com.alura.forumhub.dto.usuario.CadastrarUsuarioDTO;
import com.alura.forumhub.dto.usuario.ResponseUsuarioDTO;
import com.alura.forumhub.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @PostMapping
    public ResponseEntity<ResponseUsuarioDTO> cadastrarUsuario(@RequestBody @Valid CadastrarUsuarioDTO dto) {
        ResponseUsuarioDTO response = service.cadastrar(dto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<List<ResponseUsuarioDTO>> listarUsuarios(){
        List<ResponseUsuarioDTO> usuarios = service.listar();

        return ResponseEntity.ok().body(usuarios);
    }

}
