package com.ohho.valetparking.global.error.exception;


import com.ohho.valetparking.global.error.ErrorCode;

/**
 * Role : Responsibility : Cooperation with :
 **/
public class InvalidArgumentException extends BaseException {

  public InvalidArgumentException(String message, ErrorCode errorCode) {
    super(message, errorCode);
  }

  public InvalidArgumentException(ErrorCode errorCode) {
    super(errorCode);
  }

  public InvalidArgumentException() {
  }
}
