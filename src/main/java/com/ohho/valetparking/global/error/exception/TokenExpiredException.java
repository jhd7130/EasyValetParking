package com.ohho.valetparking.global.error.exception;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class TokenExpiredException extends RuntimeException {
    public TokenExpiredException(){}
    public TokenExpiredException(String message){
        super(message);
    }
}
