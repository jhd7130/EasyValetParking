package com.ohho.valetparking.domains.member.exception;

import com.ohho.valetparking.global.error.exception.BaseException;


public class FailVipRegisterException extends RuntimeException {

  public FailVipRegisterException() {
  }
  public FailVipRegisterException(String message) {
    super(message);
  }
}
