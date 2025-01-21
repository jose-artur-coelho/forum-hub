package com.alura.forumhub.exception.curso;

import com.alura.forumhub.exception.HttpException;

import org.springframework.http.HttpStatus;

public class CursoJaExisteException extends HttpException {
    public CursoJaExisteException(String titulo) {
        super("nome", "curso com o titulo '"+ titulo + "' já cadastrado.", HttpStatus.CONFLICT);
    }
}

