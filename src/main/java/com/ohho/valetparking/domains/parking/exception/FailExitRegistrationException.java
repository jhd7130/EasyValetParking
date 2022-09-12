package com.ohho.valetparking.domains.parking.exception;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class FailExitRegistrationException extends RuntimeException {
    public FailExitRegistrationException(){}
    public FailExitRegistrationException(String message){
        super("출차 요청 실패");
    }
}
