package com.alura.forumhub.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HttpException extends RuntimeException {
    private final Map<String, String> errors;
    private final HttpStatus httpStatus;

    public HttpException(String field, String message, HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
        this.errors = new HashMap<>();
            errors.put(field, message);
    }
}
