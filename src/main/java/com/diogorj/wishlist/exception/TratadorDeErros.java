package com.diogorj.wishlist.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity tratarErro(ApiException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
