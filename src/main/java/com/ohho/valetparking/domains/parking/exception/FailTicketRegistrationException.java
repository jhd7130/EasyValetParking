package com.ohho.valetparking.domains.parking.exception;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class FailTicketRegistrationException extends RuntimeException {
    public FailTicketRegistrationException(){}
    public FailTicketRegistrationException(String message){
        super("티켓등록 실패");
    }
}
