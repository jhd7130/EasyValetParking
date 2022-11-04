package com.ohho.valetparking.domains.member.exception;


import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;

public class SignUpFailException extends BaseException {

  public SignUpFailException() {
  }

  public SignUpFailException(String message, ErrorCode errorCode) {
    super(message,errorCode);
  }

  public SignUpFailException(ErrorCode errorCode) {
    super(errorCode);
  }
}
