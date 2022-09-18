package com.ohho.valetparking.global.common.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Role : 모든 반환 값을 대신한다.
 * Responsibility :
 * 내부에 errorResponse를 등록해야할 것인가에 대한 고민이 더 필요
 * httpstatus는 스프링에 종속 적이다. 그래서 code와 status의 경우는 그냥 만들어서 사용
 * success는 그냥 200 ok이다.
 **/
@ToString
@Getter
@EqualsAndHashCode
public class SuccessResponse<T> {
    @DateTimeFormat(pattern = "yyyy-MM-dd 'T'HH:mm")
    private final String localDateTime;
    private final boolean success;
    private final T data;
    //private final ErrorResponse errorResponse;

    public SuccessResponse(boolean success, T data ) {
        localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.success = success;
        this.data = data;
        // this.errorResponse = errorResponse;
    }

    public static <T> SuccessResponse success(T data) {
        return new SuccessResponse(true, data);
    }

}

