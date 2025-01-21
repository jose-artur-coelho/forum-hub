package com.alura.forumhub.exception.usuario;

import com.alura.forumhub.exception.HttpException;
import org.springframework.http.HttpStatus;

public class EmailJaEmUsoException extends HttpException {
    public EmailJaEmUsoException() {
        super("email", "Email já cadastrado.", HttpStatus.CONFLICT);
    }
}
