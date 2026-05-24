package com.sistema_escolar.sistema.escolar.controller.handle;

import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegistroNaoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErroResposta registroNaoEncontradoExceptionHandler(Exception e) {
        return new ErroResposta(e.getMessage(), HttpStatus.NOT_FOUND.value(), List.of());
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta registroDuplicadoExceptionHandler(Exception e) {
        return new ErroResposta(e.getMessage(), HttpStatus.CONFLICT.value(), List.of());
    }

    @ExceptionHandler(RegistroConflitanteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroResposta RegistroConflitanteExceptionHandler(Exception e) {
        return new ErroResposta(e.getMessage(), HttpStatus.CONFLICT.value(), List.of());
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ErroResposta MethodNotAllowedExceptionHandler(Exception e) {
        return new ErroResposta(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value(), List.of());
    }
}
