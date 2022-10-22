package com.ohho.valetparking.global.error.exception;

import com.ohho.valetparking.global.error.ErrorCode;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class NotFoundTokenException extends BaseException {
    public NotFoundTokenException(){}

    public NotFoundTokenException(String message,
        ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NotFoundTokenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
