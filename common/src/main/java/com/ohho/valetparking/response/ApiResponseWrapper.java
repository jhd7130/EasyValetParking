package com.ohho.valetparking.response;



import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Role : 모든 반환 값을 대신한다. Responsibility : 내부에 errorResponse를 등록해야할 것인가에 대한 고민이 더 필요 httpstatus는 스프링에
 * 종속 적이다. 그래서 code와 status의 경우는 그냥 만들어서 사용 success는 그냥 200 ok이다.
 **/

public class ApiResponseWrapper<T> {


  private final String localDateTime;
  private final boolean success;
  private final T data;

  public ApiResponseWrapper(boolean success, T data) {
    localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    this.success = success;
    this.data = data;
  }

  public static <T> ApiResponseWrapper success(T data) {
    return new ApiResponseWrapper(true, data);
  }

  // exception으로 받기
  public static ApiResponseWrapper fail(ErrorCode errorCode) {
    return new ApiResponseWrapper(false, new ErrorBody(errorCode.getCode(), errorCode.getStatus(),
        errorCode.getMessage()));
  }
  public static ApiResponseWrapper fail(ErrorCode errorCode, String message) {
    return new ApiResponseWrapper(false, new ErrorBody(errorCode.getCode(), errorCode.getStatus(),
        message));
  }

  public String getLocalDateTime() {
    return localDateTime;
  }

  public boolean isSuccess() {
    return success;
  }

  public T getData() {
    return data;
  }

  public static final class ErrorBody {

    private final String code;
    private final int status;
    private final String message;

    public ErrorBody(String code, int status, String message) {
      this.status = status;
      this.code = code;
      this.message = message;
    }

    public int getStatus() {
      return this.status;
    }

    public String getCode() {
      return code;
    }

    public String getMessage() {
      return message;
    }
  }

  public ApiResponseWrapper(String localDateTime, boolean success, T data) {
    this.localDateTime = localDateTime;
    this.success = success;
    this.data = data;
  }
}

