package com.ohho.valetparking.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.error.exception.BaseException;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import com.ohho.valetparking.global.error.exception.UnExpectedException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

/**
 * Role : Responsibility : Cooperation with : ResponseEntityExceptionHandler는 미리 발생 가능한 예외들을 처리해놓은
 * 집합체 기존에 스프링에서 발생하던 예외들을 처리해준다.
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  //-------------------------------------------- Server---------------------------------------
  @ExceptionHandler(value = {DataIntegrityViolationException.class,
      ConstraintViolationException.class})
  protected ApiResponse handleDataException() {
    HttpStatus httpStatus = HttpStatus.CONFLICT;
    log.error("handleDataException throw Exception : {}", httpStatus);
    return ApiResponse.fail(ErrorCode.DATA_DUPLICATE);
  }

  /*
   애플리케이션에서 지정하지 않은 (예상치 못한) 에러(baseException)처리
   BaseException 으로 바로 cast할 수 없기 때문에,exception 에 감싸서 넣어준다.
  */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> unExpectedExceptionHandler(
      Exception e, HttpServletRequest request) {
    log.error("unhandled exception", e);
    UnExpectedException exception = new UnExpectedException(e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ErrorResponse.builder()
                .code("Server")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build()
        );
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    log.info("GlobalExceptionHandler :: MethodArgumentNotValidException = {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .code("Reqeust Parameter")
            .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
        );
  }

  @ExceptionHandler
  protected ResponseEntity<ErrorResponse> handleJsonProcessingException(
      JsonProcessingException ex) {
    log.info("GlobalExceptionHandler :: JsonProcessingException = {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .code("Jwt Token Binding")
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
        );
  }

  //-------------------------------------------- Custom---------------------------------------
  // 하나의 메서드로 처리가 가능할 것 같다. -> 기준이 되는 RuntimeException을 확장한 base exception을 만들고
  // Custom Exception들을 모두 확장시키는 방향으로
  // 그리고 나머지 다 지우고 base Exception을 처리하는 @ExceptionHandler 하나만 두기

  @ExceptionHandler(BaseException.class)
  protected ApiResponse handleBaseException(BaseException e) {
    log.info("GlobalExceptionHandler :: {} = {} ", e.getClass().getName(), e.getMessage());
    return ApiResponse.fail(e.getErrorCode());
  }
}
