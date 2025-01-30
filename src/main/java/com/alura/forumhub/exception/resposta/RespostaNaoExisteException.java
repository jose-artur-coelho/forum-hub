package com.alura.forumhub.exception.resposta;

import com.alura.forumhub.exception.HttpException;
import org.springframework.http.HttpStatus;

public class RespostaNaoExisteException extends HttpException {
    public RespostaNaoExisteException(Long id) {
        super("id", "A resposta com id " + id + " não existe." , HttpStatus.NOT_FOUND);
    }
}
