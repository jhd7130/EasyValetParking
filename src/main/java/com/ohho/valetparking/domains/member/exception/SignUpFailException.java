package com.ohho.valetparking.domains.member.exception;


public class SignUpFailException extends RuntimeException {
    public SignUpFailException(){}
    public SignUpFailException(String message){
        super(message);
    }
}
