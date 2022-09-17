package com.ohho.valetparking.domains.parking.exception;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class FailTicketRegistrationException extends RuntimeException {
    public FailTicketRegistrationException(){
        super("티켓등록 실패");

    }
    public FailTicketRegistrationException(String message){
        super("티켓등록 실패");

    }
}
