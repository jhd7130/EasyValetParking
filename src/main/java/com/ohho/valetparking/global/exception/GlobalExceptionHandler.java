package com.ohho.valetparking.global.exception;

import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.global.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

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
                                                  .code("Sign-Up Fail")
                                                  .message(e.getMessage())
                                                  .status(HttpStatus.NOT_FOUND)
                                                  .build()
                                    );
    }
}
