package com.ohho.valetparking.global.Interceptor;

import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.global.security.JWTProvider;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Role : ControllerAdvice
 * 예외 처리 전략이 ControllerAdviced이지만 처리되는 이유는 FrontController인 dispatcherServlet이
 * 받은 예외를 처리할 수 없을 경우 HandlerInterceptor를 구현한 객체에게 예외 처리를 요청한다.
 **/

@Slf4j
public class AuthLoginInterceptor implements HandlerInterceptor {
    // 테스트 굉장히 많이 필요 2022.08.26
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("[AuthLoginInterceptor] preHandle in :: AccessToken is  = {} ",request.getHeader("ACCESSTOKEN"));

        if( request.getHeader("ACCESSTOKEN") != null && JWTProvider.isValid( request.getHeader("ACCESSTOKEN")) ) {
            return true;
        }

        String refreshToken = request.getHeader("REFRESHTOKEN");


        if ( refreshToken != null && JWTProvider.isValid(refreshToken)){
            // 리프레시토큰에서 회원정보(아이디,부서)얻어오기
            response.setHeader("ACCESSTOKEN",JWTProvider.accessTokenCreate(JWTProvider.getSignInFromToken(refreshToken)));
        }

        throw new IllegalAccessException("유효하지 않은 토큰입니다.");
    }
}
