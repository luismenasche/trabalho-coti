package com.example.trabalho_coti.controller;

import com.example.trabalho_coti.dto.error.*;
import com.example.trabalho_coti.exception.CPFDuplicadoException;
import com.example.trabalho_coti.exception.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ClienteControllerExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroJsonDTO> jsonError() {
        return new ResponseEntity<>(new ErroJsonDTO(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacaoDTO> validationError(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getFieldErrors().forEach(erro -> erros.put(erro.getField(), erro.getDefaultMessage()));
        return new ResponseEntity<>(new ErroValidacaoDTO(erros), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErroIdDTO> idNotFound(IdNotFoundException ex) {
        return new ResponseEntity<>(ErroIdDTO.build(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CPFDuplicadoException.class)
    public ResponseEntity<ErroCPFDTO> dataIntegrityError() {
        return new ResponseEntity<>(new ErroCPFDTO(), HttpStatus.CONFLICT);
    }
}
