package com.sistema_escolar.sistema.escolar.controller.handle;

import com.sistema_escolar.sistema.escolar.data.dto.ErroCampo;
import com.sistema_escolar.sistema.escolar.data.dto.ErroResposta;
import com.sistema_escolar.sistema.escolar.exception.RegistroConflitanteException;
import com.sistema_escolar.sistema.escolar.exception.RegistroDuplicadoException;
import com.sistema_escolar.sistema.escolar.exception.RegistroNaoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ErroResposta registroConflitanteExceptionHandler(Exception e) {
        return new ErroResposta(e.getMessage(), HttpStatus.CONFLICT.value(), List.of());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErroResposta accessControlExceptionHandler(Exception e) {
        return new ErroResposta(e.getMessage(), HttpStatus.UNAUTHORIZED.value(), List.of());
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroResposta methodArgumentNotValidExceptioHandler(MethodArgumentNotValidException e) {

        List<FieldError> fieldErrors = e.getFieldErrors();

        List<ErroCampo> errosCampos = fieldErrors
                .stream()
                .map(fieldError -> new ErroCampo(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        return new ErroResposta("Erro de validação", HttpStatus.UNPROCESSABLE_ENTITY.value(), errosCampos);
    }
}
