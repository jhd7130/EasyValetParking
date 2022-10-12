package com.ohho.valetparking.domains.member.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

public class SignInFailException extends BaseException {

    public SignInFailException(){}
    public SignInFailException(String message, ErrorCode errorCode){
        super(message,errorCode);
    }
    public SignInFailException(ErrorCode errorCode){
        super(errorCode);
    }
}
