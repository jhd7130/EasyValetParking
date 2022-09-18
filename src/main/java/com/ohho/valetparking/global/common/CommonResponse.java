package com.ohho.valetparking.global.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class CommonResponse {
    public static final ResponseEntity SUECCESS_RESPONSE = ResponseEntity.status(HttpStatus.OK).build();
}
