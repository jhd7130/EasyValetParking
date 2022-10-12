package com.ohho.valetparking.domains.member.exception;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;


public class FailVipRegisterException extends BaseException {

  public FailVipRegisterException() {
  }
  public FailVipRegisterException(ErrorCode errorCode){
    super(errorCode);
  }
  public FailVipRegisterException(String message, ErrorCode errorCode) {
    super(message,errorCode);
  }
}
