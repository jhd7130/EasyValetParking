package com.ohho.valetparking.global.error;

import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.http.ResponseEntity;


@Getter // 시리얼라이즈가 필요한 객체일 경우에는 Getter 메서드가 꼭 필요하다.
@ToString
public class ErrorResponse {
    @DateTimeFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
    private final String localDateTime;
    private final String code;
    private final String message;
    private final HttpStatus status;

    private ErrorResponse(ErrorBuilder errorBuilder) {
        this.localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.code = errorBuilder.code;
        this.message = errorBuilder.message;
        this.status = errorBuilder.status;
    }

    public static ErrorBuilder builder(){
        return new ErrorBuilder();
    }

    public static class ErrorBuilder{
        private LocalDateTime localDateTime = LocalDateTime.now();
        private String code;
        private String message;
        private HttpStatus status;
        public ErrorBuilder() {
        }

        public ErrorBuilder code(String code) {
            this.code = code;
            return this;
        }

        public ErrorBuilder message(String message) {
            this.message = message;
            return this;
        }
        public ErrorBuilder status(HttpStatus status) {
            this.status = status;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }

}
