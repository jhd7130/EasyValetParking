package com.ohho.valetparking.domains.parking.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

/**
 * Role : Responsibility : Cooperation with :
 **/
public class NotFoundExitRequestException extends BaseException {

  public NotFoundExitRequestException() {
  }

  public NotFoundExitRequestException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public NotFoundExitRequestException(ErrorCode errorCode) {
    super(errorCode);
  }
}
