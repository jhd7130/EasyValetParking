package com.ohho.valetparking.domains.parking.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

/**
 * Role : Responsibility : Cooperation with :
 **/
public class FailParkingRegistrationException extends BaseException {

  public FailParkingRegistrationException() {
  }

  public FailParkingRegistrationException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public FailParkingRegistrationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
