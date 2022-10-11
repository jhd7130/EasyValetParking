package com.ohho.valetparking.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
public enum ErrorCode {

    // Common
    INVALID_ARGUMENT(400, "C001", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "기능 사용 권한이 없습니다."),
    HANDLE_ACCESS_DENIED(403, "C006", "권한이 없습니다."),

    // Member
    EMAIL_DUPLICATION(400, "M001", "중복된 이메일이 존재합니다."),
    LOGIN_INPUT_INVALID(400, "M002", "로그인 정보가 없습니다.")

    ;
    private final int status;
    private final String code;
    private final String message;


    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
