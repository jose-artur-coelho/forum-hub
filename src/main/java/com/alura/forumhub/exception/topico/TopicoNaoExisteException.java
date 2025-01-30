package com.alura.forumhub.exception.topico;

import com.alura.forumhub.exception.HttpException;
import org.springframework.http.HttpStatus;

public class TopicoNaoExisteException extends HttpException {
  public TopicoNaoExisteException(Long id) {
    super("topico", "O tópico com o id " + id + " não existe.", HttpStatus.NOT_FOUND);
  }
}
