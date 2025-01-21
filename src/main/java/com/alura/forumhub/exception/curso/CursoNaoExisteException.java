package com.alura.forumhub.exception.curso;

import com.alura.forumhub.exception.HttpException;
import org.springframework.http.HttpStatus;

public class CursoNaoExisteException extends HttpException {
    public CursoNaoExisteException() {
        super("curso", "O curso indicado não existe", HttpStatus.NOT_FOUND);
    }
}
