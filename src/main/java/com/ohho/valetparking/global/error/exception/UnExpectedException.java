package com.ohho.valetparking.global.error.exception;

/**
 * Role : Responsibility : Cooperation with :
 **/
public class UnExpectedException extends Exception {
  public UnExpectedException(){}
  public UnExpectedException(String message){
    super("예상하지 못한 에러입니다.");
  }
  public UnExpectedException(Exception e) {
  }
}
