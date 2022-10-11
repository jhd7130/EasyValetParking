package com.ohho.valetparking.domains.member.exception;

public class SignInFailException extends RuntimeException {

    public SignInFailException(){}
    public SignInFailException(String message){
        super(message);
    }
}
