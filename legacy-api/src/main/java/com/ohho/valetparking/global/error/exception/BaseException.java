package com.ohho.valetparking.global.error.exception;

import com.ohho.valetparking.global.error.ErrorCode;

/**
 * @explain System전체에서 사용할 최상위 커스텀 예외
 * @HOWTO 모든 커스텀 예외는 이 예외를 확장해서 사용할 것.
 * @Spec ErrorCode를 사용함으로 에러 코드로 모든 설명을 하게됨.
 **/
public class BaseException extends RuntimeException {

  private ErrorCode errorCode;

  public BaseException(String message, ErrorCode errorCode) {
    super(message);
    this.errorCode = errorCode;
  }

  public BaseException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }
  public BaseException(){}
  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
