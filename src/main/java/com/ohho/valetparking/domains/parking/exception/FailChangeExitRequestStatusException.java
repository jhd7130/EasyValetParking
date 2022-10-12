package com.ohho.valetparking.domains.parking.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class FailChangeExitRequestStatusException extends BaseException {
    public FailChangeExitRequestStatusException(){}
    public FailChangeExitRequestStatusException(String message, ErrorCode errorCode){
        super(message,errorCode);
    }
    public FailChangeExitRequestStatusException(ErrorCode errorCode){
        super(errorCode);
    }
}
