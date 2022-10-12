package com.ohho.valetparking.global.error.exception;

import com.ohho.valetparking.global.error.ErrorCode;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class TokenExpiredException extends BaseException {
    public TokenExpiredException(){}

    public TokenExpiredException(String message,
        ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TokenExpiredException(ErrorCode errorCode) {
        super(errorCode);
    }
}
