package com.diogorj.wishlist.exception;

public class ApiException extends RuntimeException {

    public ApiException(String mensagem) {
        super(mensagem);
    }
}
