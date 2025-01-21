package com.alura.forumhub.dto.usuario;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CadastrarUsuarioDTO(
        @NotBlank(message = "O nome do usuário é obrigatório.")
        String nome,

        @NotBlank(message = "O email do usuário é obrigatório.")
        @Email(message = "Formato de email inválido.")
        String email,

        @NotBlank(message = "A senha do usuário é obrigatória.")
        String senha
) {
}
