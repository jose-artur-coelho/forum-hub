package com.alura.forumhub.exception.topico;

import com.alura.forumhub.exception.HttpException;
import org.springframework.http.HttpStatus;

public class TopicoNaoExisteException extends HttpException {
  public TopicoNaoExisteException() {
    super("topico", "O tópico indicado não existe", HttpStatus.NOT_FOUND);
  }
}
