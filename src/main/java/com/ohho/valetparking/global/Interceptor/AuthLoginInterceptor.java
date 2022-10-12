package com.ohho.valetparking.global.Interceptor;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Role : ControllerAdvice 예외 처리 전략이 ControllerAdviced이지만 처리되는 이유는 FrontController인
 * dispatcherServlet이 받은 예외를 처리할 수 없을 경우 HandlerInterceptor를 구현한 객체에게 예외 처리를 요청한다.
 **/

@Slf4j
@Component
public class AuthLoginInterceptor implements HandlerInterceptor {


  private final JWTProvider jwtProvider;

  public AuthLoginInterceptor(JWTProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (request.getHeader("ACCESSTOKEN") != null && jwtProvider.isValid(
        request.getHeader("ACCESSTOKEN"))) {
      return true;

    }
    log.info("test");
//        String refreshToken = request.getHeader("REFRESHTOKEN");
//
//
//        if ( refreshToken != null && jwtProvider.isValid(refreshToken)){
//            // 리프레시토큰에서 회원정보(아이디,부서)얻어오기
//            response.setHeader("ACCESSTOKEN",jwtProvider.accessTokenCreate(jwtProvider.getEmailInFromToken(refreshToken)));
//        }

    throw new TokenExpiredException(ErrorCode.INVALID_TOKEN);
  }
}
