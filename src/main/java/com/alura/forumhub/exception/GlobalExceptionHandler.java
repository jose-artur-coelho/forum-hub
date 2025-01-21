package com.alura.forumhub.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<String> errorMessages = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> {
                    if (error instanceof FieldError fieldError) {
                        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
                    }
                    return error.getDefaultMessage();
                })
                .collect(Collectors.toList());

        Map<String, Object> body = createErrorResponse(
                HttpStatus.BAD_REQUEST,
                errorMessages,
                ex.getParameter().getExecutable().getName());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<Map<String, Object>> handleHttpException(HttpException ex, HttpServletRequest request) {
        Map<String, Object> body = createErrorResponse(ex.getHttpStatus() ,ex.getErrors(), request.getRequestURI());

        return ResponseEntity.status(ex.getHttpStatus()).body(body);

    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(HttpServletRequest request) {

        Map<String, String> messages = new HashMap<>();
        messages.put("erro","Entidade não encontrada");
        Map<String, Object> body = createErrorResponse(HttpStatus.NOT_FOUND, messages, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpServletRequest request) {

        Map<String, String> messages = new HashMap<>();
        messages.put("erro", ex.getMessage());
        Map<String, Object> body = createErrorResponse(HttpStatus.BAD_REQUEST, messages, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(HttpServletRequest request) {

        Map<String, String> messages = new HashMap<>();
        messages.put("erro", "Credenciais inválidas");
        Map<String, Object> body = createErrorResponse(HttpStatus.UNAUTHORIZED, messages, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationError(HttpServletRequest request) {

        Map<String, String> messages = new HashMap<>();
        messages.put("erro", "Falha na autenticação.");
        Map<String, Object> body = createErrorResponse(HttpStatus.UNAUTHORIZED, messages, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAcessDenied(HttpServletRequest request) {

        Map<String, String> messages = new HashMap<>();
        messages.put("erro", "Você não possui autorização para fazer essa requisição.");
        Map<String, Object> body = createErrorResponse(HttpStatus.FORBIDDEN, messages, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleInternalServerError(Exception ex, HttpServletRequest request) {

        Map<String, String> messages = new HashMap<>();
        messages.put("erro", ex.getMessage());
        Map<String, Object> body = createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, messages, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }


    private Map<String, Object> createErrorResponse(HttpStatus status, Object messages, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.toString());
        body.put("messages", messages);
        body.put("path", path);
        return body;
    }

}