package com.ohho.valetparking.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.error.exception.BaseException;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import com.ohho.valetparking.global.error.exception.UnExpectedException;
import io.swagger.annotations.Api;
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
 * Role : 어플리케이션 내에서 발생하는 모든 Exception을 처리해 줍니다. Cooperation with : ResponseEntityExceptionHandler(
 * Spring에서 미리 발생 가능한 예외들을 처리해놓은 집합체 기존에 스프링에서 발생하던 예외들을 처리해준다.)
 *
 * @auth Atom
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  //-------------------------------------------- Server---------------------------------------

  /**
   * DB관련 Exception을 처리
   */
  @ExceptionHandler(value = {DataIntegrityViolationException.class,
      ConstraintViolationException.class})
  protected ApiResponse handleDataException() {
    HttpStatus httpStatus = HttpStatus.CONFLICT;
    log.error("handleDataException throw Exception : {}", httpStatus);
    return ApiResponse.fail(ErrorCode.DATA_DUPLICATE);
  }

  /**
   * 애플리케이션에서 지정하지 않은 (예상치 못한) 에러(baseException)처리 BaseException 으로 바로 cast할 수 없기 때문에,exception 에
   * 감싸서 넣어준다.
   *
   * @auth Atom
   **/
  @ExceptionHandler(Exception.class)
  protected ApiResponse unExpectedExceptionHandler(
      Exception e, HttpServletRequest request) {
    log.error("unhandled exception", e);
    UnExpectedException exception = new UnExpectedException(e);
    return ApiResponse.fail(ErrorCode.SERVER_ERROR);
  }

  /**
   * @param ex      the exception
   * @param headers the headers to be written to the response
   * @param status  the selected response status
   * @param request the current request
   * @return
   */
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


  /**
   * 모든 커스텀 exception은 BaseException을 상속받게 구현됩니다. 위와 같이 한 이유는 하나의 @ExceptionHandler를 통해 중복되는 코드들을
   * 줄이기 위해서 입니다.
   *
   * @auth Atom
   */
  @ExceptionHandler(BaseException.class)
  protected ApiResponse handleBaseException(BaseException e) {
    log.info("GlobalExceptionHandler :: {} = {} ", e.getClass().getName(), e.getMessage());
    return ApiResponse.fail(e.getErrorCode());
  }
}
