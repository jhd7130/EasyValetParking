package com.ohho.valetparking.domains.parking.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class FailTicketRegistrationException extends BaseException {
    public FailTicketRegistrationException(){
        super(ErrorCode.FAIL_REGISTER);
    }

    public FailTicketRegistrationException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public FailTicketRegistrationException(ErrorCode errorCode) {
        super(errorCode);
    }
}
