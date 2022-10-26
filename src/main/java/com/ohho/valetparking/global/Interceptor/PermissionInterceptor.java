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

    TokenIngredient tokenIngredient = getTokenIngredient(request);

    PermissionRequired permissionRequired = getPermissionRequired(handler);

    log.info("tokenIngredient={} permissionRequired ={}", tokenIngredient, permissionRequired);

    if (Objects.isNull(permissionRequired)) {
      return true;
    }
    if (Objects.nonNull(permissionRequired) && validateAdmin(permissionRequired, tokenIngredient)) {
      return true;
    }

    throw new BaseException(ErrorCode.METHOD_NOT_ALLOWED);
  }

  private boolean validateAdmin(PermissionRequired permissionRequired,
      TokenIngredient tokenIngredient) {
    //if (permissionRequired.permission().equals(MemberType.ADMIN) && tokenIngredient.isAdmin()) {
    if (permissionRequired.permission().equals(MemberType.ADMIN)
        && tokenIngredient.getDepartment() == 0) {
      return true;
    }

    return false;
  }

  private PermissionRequired getPermissionRequired(Object handler) {
    HandlerMethod handlerMethod = (HandlerMethod) handler;
    return handlerMethod.getMethodAnnotation(
        PermissionRequired.class);
  }

  private TokenIngredient getTokenIngredient(HttpServletRequest request)
      throws JsonProcessingException {
    try {
      return jwtProvider.getTokenIngredientFromToken(request.getHeader("ACCESSTOKEN"));
    } catch (RuntimeException e) {
      return jwtProvider.getTokenIngredientFromToken(request.getHeader("NEWACCESSTOKEN"));
    }
  }
}
