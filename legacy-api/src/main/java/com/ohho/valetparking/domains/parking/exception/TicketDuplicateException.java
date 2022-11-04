package com.ohho.valetparking.domains.parking.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class TicketDuplicateException extends BaseException {
    public TicketDuplicateException(){}

    public TicketDuplicateException(String message,
        ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TicketDuplicateException(ErrorCode errorCode) {
        super(errorCode);
    }
}
