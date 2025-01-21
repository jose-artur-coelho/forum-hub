package com.alura.forumhub.service;

import com.alura.forumhub.domain.usuario.PapelUsuario;
import com.alura.forumhub.domain.usuario.Usuario;
import com.alura.forumhub.dto.usuario.CadastrarUsuarioDTO;
import com.alura.forumhub.dto.usuario.ResponseUsuarioDTO;
import com.alura.forumhub.exception.usuario.EmailJaEmUsoException;
import com.alura.forumhub.repository.UsuarioRepository;
import com.alura.forumhub.security.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService implements UserDetailsService {
    @Autowired
    UsuarioRepository repository;

    public ResponseUsuarioDTO cadastrar(CadastrarUsuarioDTO dto) {
        boolean emailJaCadastrado = repository.existsByEmail(dto.email());

        if (emailJaCadastrado) {
            throw new EmailJaEmUsoException();
        }

        Usuario usuario = Usuario.builder().nome(dto.nome()).email(dto.email())
                .senha(PasswordUtil.encryptPassword(dto.senha())).papel(PapelUsuario.COMUM)
                .build();

        repository.save(usuario);

        return new ResponseUsuarioDTO(usuario);

    }

    public List<ResponseUsuarioDTO> listar() {
        List<Usuario> usuarios = repository.findAll();
        return usuarios.stream().map(ResponseUsuarioDTO::new).toList();
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username);
    }
}
