package com.ohho.valetparking.domains.parking.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class FailExitRegistrationException extends BaseException {
    public FailExitRegistrationException(){}
    public FailExitRegistrationException(ErrorCode errorCode){
        super(errorCode);
    }
    public FailExitRegistrationException(String message, ErrorCode errorCode){
        super(message,errorCode);
    }
}
