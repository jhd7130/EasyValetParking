package com.ohho.valetparking.global.error;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.parking.exception.FailChangeExitRequestStatusException;
import com.ohho.valetparking.domains.parking.exception.FailExitRegistrationException;
import com.ohho.valetparking.domains.parking.exception.FailTicketRegistrationException;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import com.ohho.valetparking.global.error.exception.UnExpectedException;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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

  /*
   애플리케이션에서 지정하지 않은 (예상치 못한) 에러(baseException)처리
   BaseException 으로 바로 cast할 수 없기 때문에,exception 에 감싸서 넣어준다.
  */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> unExpectedExceptionHandler(
      Exception e, HttpServletRequest request) {
    log.error("unhandled exception", e);
    UnExpectedException exception = new UnExpectedException(e);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
        ErrorResponse.builder()
            .code("Server")
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
    );
  }


  //-------------------------------------------- Custom---------------------------------------
  // 하나의 메서드로 처리가 가능할 것 같다. -> 기준이 되는 RuntimeException을 확장한 base exception을 만들고
  // Custom Exception들을 모두 확장시키는 방향으로
  // 그리고 나머지 다 지우고 base Exception을 처리하는 @ExceptionHandler 하나만 두기
  @ExceptionHandler(SignUpFailException.class)
  protected ResponseEntity<ErrorResponse> handleSignUpFailException(SignUpFailException e) {
    log.info("GlobalExceptionHandler :: SignUpFailException = {} ", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ErrorResponse.builder()
                .code("Sign-Up")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build()
        );
  }

  @ExceptionHandler(SignInFailException.class)
  protected ResponseEntity<ErrorResponse> handleSignInFailException(SignInFailException e) {
    log.info("GlobalExceptionHandler :: SignInFailException = {} ", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .code("Sign-In")
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
        );
  }

  @ExceptionHandler(TokenExpiredException.class)
  protected ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException e) {
    log.info("GlobalExceptionHandler :: TokenExpiredException = {} ", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(ErrorResponse.builder()
            .code("Token")
            .message(e.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
        );
  }

  @ExceptionHandler(TicketDuplicateException.class)
  protected ResponseEntity<ErrorResponse> handleTicketDuplicateException(
      TicketDuplicateException e) {
    log.info("GlobalExceptionHandler :: TicketDuplicateException = {} ", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ErrorResponse.builder()
                .code("Ticket")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build()
        );
  }

  @ExceptionHandler(FailTicketRegistrationException.class)
  protected ResponseEntity<ErrorResponse> handleFailTicketRegistrationException(
      FailTicketRegistrationException e) {
    log.info("GlobalExceptionHandler :: FailTicketRegistrationException = {} ", e.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ErrorResponse.builder()
                .code("Ticket")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST)
                .build()
        );
  }

  @ExceptionHandler(FailExitRegistrationException.class)
  protected ResponseEntity<ErrorResponse> handleFailExitRegistrationException(
      FailExitRegistrationException ex) {
    log.info("GlobalExceptionHandler :: FailExitRegistrationException = {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .code("Reqeust Parameter")
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
        );
  }

  @ExceptionHandler(FailChangeExitRequestStatusException.class)
  protected ResponseEntity<ErrorResponse> handleFailChangeExitRequestStatusException(
      FailChangeExitRequestStatusException ex) {
    log.info("GlobalExceptionHandler :: FailChangeExitRequestStatusException = {}",
        ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .code("Reqeust Status")
            .message(ex.getMessage())
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

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex) {
    log.info("GlobalExceptionHandler :: ConstraintViolationException = {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(ErrorResponse.builder()
            .code("Reqeust Parameter")
            .message(ex.getMessage())
            .status(HttpStatus.BAD_REQUEST)
            .build()
        );
  }
}
