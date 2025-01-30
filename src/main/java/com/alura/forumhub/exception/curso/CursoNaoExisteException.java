package com.alura.forumhub.exception.curso;

import com.alura.forumhub.exception.HttpException;
import org.springframework.http.HttpStatus;

public class CursoNaoExisteException extends HttpException {
    public CursoNaoExisteException(Long id) {
        super("id", "O curso com o id "+ id + " não existe.", HttpStatus.NOT_FOUND);
    }
}
