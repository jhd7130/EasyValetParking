package com.ohho.valetparking.global.Interceptor;

import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.TokenExpiredException;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import com.ohho.valetparking.global.util.SessionUtil;
import java.util.Objects;
import java.util.Optional;
import javax.swing.text.html.Option;
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
  /**
   *    AccessToken을 우선적으로 검사합니다.
   *    만료되었을 경우 RefreshToken을 검사합니다.
   *    RefreshToken이 만료되지 않았을 경우 AccessToken을 재발급합니다.
   *    RefreshToken이 없거나 만료되었을 경우에는 재로그인을 위한 예외를 발생시킵니다.
   *
   *    * @author Atom
   *    * @param session 사용자의 세션
   *    * @return refreshToken Value를 반환
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    // Accesstoken 유효성 검증 후 실패시 아래 명렁 실행
    if (request.getHeader("ACCESSTOKEN") != null && jwtProvider.isValid(
        request.getHeader("ACCESSTOKEN"))) {
      return true;
    }
    // Accesstoken이 만료된 경우 토큰 유효성 검증
    String refreshtoken = SessionUtil.getRefreshtoken(request.getSession());

    // 만약 refreshToken을 세션에서 찾을 수 없다면 재로그인 반환
    isRefreshTokenExpired(refreshtoken);

    if (jwtProvider.isValid(refreshtoken)) {
      TokenIngredient tokenIngredientFormRefreshToken = jwtProvider.getTokenIngredientFromToken(
          refreshtoken);
      String newAccessToken = jwtProvider.accessTokenCreate(tokenIngredientFormRefreshToken);
      response.addHeader("ACCESSTOKEN", newAccessToken);

      return true;
    }

    throw new TokenExpiredException(ErrorCode.INVALID_TOKEN);
  }

  private void isRefreshTokenExpired(String refreshToken){
    if (Objects.isNull(refreshToken) && refreshToken.isBlank()) {
      throw new TokenExpiredException(ErrorCode.INVALID_TOKEN);
    }
  }

}
