package com.alura.forumhub.exception;

import org.springframework.http.HttpStatus;

public class OperacaoInvalidaException extends HttpException {
    public OperacaoInvalidaException() {
        super("erro", "Você não tem autorização para essa operação", HttpStatus.FORBIDDEN);
    }
}
