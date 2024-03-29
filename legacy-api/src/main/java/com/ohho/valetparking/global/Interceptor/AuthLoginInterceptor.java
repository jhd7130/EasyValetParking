package com.ohho.valetparking.global.Interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
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
   * AccessToken을 우선적으로 검사합니다. 만료되었을 경우 RefreshToken을 검사합니다.
   * RefreshToken이 만료되지 않았을 경우 AccessToken을 재발급합니다.
   * RefreshToken이 없거나 만료되었을 경우에는 재로그인을 위한 예외를 발생시킵니다.
   *
   * @author Atom
   */
  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    String accessToken = request.getHeader("ACCESSTOKEN");

    // 엑세스토큰 만료 확인
    if (isAccesstokenExpired(accessToken)) {
      return true;
    }

    // RefreshToken이 없을 시 함께 요청 반려 재 요청필요. 특정 예외 반환을 위한 if 문
    if (Objects.isNull(request.getHeader("REFRESHTOKEN"))) {
      throw new TokenExpiredException(ErrorCode.INVALID_ACCESSTOKEN_NOT_FOUND_REFRESHTOKEN);
    }

    // Accesstoken이 만료된 경우 RefreshToken 유효성 검증 후 RefreshToken의 Claim에서 TokenIngredient을 가져온다.
    TokenIngredient tokenIngredientFormRefreshToken =
        isRefreshTokenExpired(request.getHeader("REFRESHTOKEN"));
        // isRefreshTokenExpired(SessionUtil.getRefreshtoken(request.getSession())); // When using Session storage with Redis

    // TODO : Token 값객체로 만들어서 하나로 반환
    String newAccessToken = jwtProvider.accessTokenCreate(tokenIngredientFormRefreshToken);
    String newRefreshToken = jwtProvider.refreshTokenCreate(tokenIngredientFormRefreshToken);

    addNewTokenToMessageFrom(request,response,newAccessToken,newRefreshToken);

    return true;
  }

  /**
   * refreshToken을 주면 해당 토큰의 유효성을 검증합니다.
   * @auth refreshToken
   * @return TokenIngredient
   * @throws JsonProcessingException
   */
  private TokenIngredient isRefreshTokenExpired(String refreshToken)
      throws JsonProcessingException {
    if (Objects.isNull(refreshToken) && refreshToken.isBlank()
        && !jwtProvider.isValid(refreshToken)) {
      throw new TokenExpiredException(ErrorCode.INVALID_REFRESHTOKEN);
    }
    return jwtProvider.getTokenIngredientFromToken(refreshToken);
  }

  // accessToken을 검증하여 반환합니다.
  private boolean isAccesstokenExpired(String accessToken) {

    log.info("accessToken ={}", accessToken);
    return accessToken != null && jwtProvider.isValid(accessToken);

  }

  // 새로운 토큰을 각 메세지에 추가합니다.
  private void addNewTokenToMessageFrom(HttpServletRequest request,HttpServletResponse response,String newAccessToken, String newRefreshToken){

    response.addHeader("ACCESSTOKEN", newAccessToken);
    response.addHeader("REFRESHTOKEN", newRefreshToken);

    request.setAttribute("NEWACCESSTOKEN", newAccessToken);
    request.setAttribute("NEWREFRESHTOKEN", newRefreshToken);

  }
}
