package com.ohho.valetparking.global.error;

import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.parking.exception.FailExitRegistrationException;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
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
 * Role :
 * Responsibility :
 * Cooperation with :
 * ResponseEntityExceptionHandler는 미리 발생 가능한 예외들을 처리해놓은 집합체 기존에 스프링에서 발생하던 예외들을 처리해준다.
 *

 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
                             .body( ErrorResponse.builder()
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
                             .body( ErrorResponse.builder()
                                                  .code("Token")
                                                  .message(e.getMessage())
                                                  .status(HttpStatus.BAD_REQUEST)
                                                  .build()
                                    );
    }
    @ExceptionHandler(TicketDuplicateException.class)
    protected ResponseEntity<ErrorResponse> TicketDuplicateException( TicketDuplicateException e ) {
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



    @ExceptionHandler(FailExitRegistrationException.class)
    protected ResponseEntity<Object> handleFailExitRegistrationException( FailExitRegistrationException ex ) {
        log.info("GlobalExceptionHandler :: MethodArgumentNotValidException = {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                               .body(ErrorResponse.builder()
                                               .code("Reqeust Parameter")
                                               .message(ex.getMessage())
                                               .status(HttpStatus.BAD_REQUEST)
                                               .build()
                );
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request ) {
        log.info("GlobalExceptionHandler :: MethodArgumentNotValidException = {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(ErrorResponse.builder()
                             .code("Reqeust Parameter")
                             .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                             .status(HttpStatus.BAD_REQUEST)
                             .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolationException( ConstraintViolationException ex ) {
        log.info("GlobalExceptionHandler :: ConstraintViolationException = {}",ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponse.builder()
                        .code("Reqeust Parameter")
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST)
                        .build()
                );
    }
}
