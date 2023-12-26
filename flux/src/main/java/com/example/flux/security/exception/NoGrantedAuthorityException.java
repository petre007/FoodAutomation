package com.example.flux.security.exception;

public class NoGrantedAuthorityException extends Exception{

    public NoGrantedAuthorityException(String message) {
        super(message);
    }
}
