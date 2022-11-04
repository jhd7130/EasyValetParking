package com.ohho.valetparking.global.Interceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.domains.member.enums.MemberType;
import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import com.ohho.valetparking.global.security.permission.PermissionRequired;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;


@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

  private final JWTProvider jwtProvider;

  public PermissionInterceptor(JWTProvider jwtProvider) {
    this.jwtProvider = jwtProvider;
  }

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {

    // 사용자의 토큰에서 권한 정보를 가져옵니다.
    // Entry Point에서 Annotation 정보를 가져옵니다.
    TokenIngredient tokenIngredient = getTokenIngredient(request);
    PermissionRequired permissionRequired = getPermissionRequired(handler);

    log.info("tokenIngredient={} permissionRequired ={}", tokenIngredient, permissionRequired);

    // Entry Point 기능 권한체크 필요 여부 확인/ 필요없을 경우 통과
    if (Objects.isNull(permissionRequired)) {
      return true;
    }

    // Entry Point의 권한과 사용자의 권한 일치 여부 확인
    if (validateAdmin(permissionRequired, tokenIngredient)) {
      return true;
    }

    throw new BaseException(ErrorCode.METHOD_NOT_ALLOWED);
  }

  /**
   * 권한을 확인 사용자 관리자 둘 뿐이기에 둘 중 하나를 확인
   * @Auth Doo
   */
  private boolean validateAdmin(PermissionRequired permissionRequired,
      TokenIngredient tokenIngredient) {
    if (permissionRequired.permission().equals(MemberType.ADMIN)
        && tokenIngredient.getDepartment() == 0) {
      return true;
    }

    return false;
  }

  /**
   * HandlerMapping에서 사용자 권한을 가져와서 반환
   * @Auth Doo
   */
  private PermissionRequired getPermissionRequired(Object handler) {
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    return handlerMethod.getMethodAnnotation(
        PermissionRequired.class);
  }

  /**
   * AccessToken이 재발급된 경우 재발급 된 토큰을 확인
   * @Auth Doo
   */
  private TokenIngredient getTokenIngredient(HttpServletRequest request)
      throws JsonProcessingException {
    try {
      return jwtProvider.getTokenIngredientFromToken(request.getHeader("ACCESSTOKEN"));
    } catch (RuntimeException e) {
      return jwtProvider.getTokenIngredientFromToken(request.getHeader("NEWACCESSTOKEN"));
    }
  }
}
