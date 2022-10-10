package com.ohho.valetparking.global.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Role : json으로 값이 들어올 때 enum의 상수값이 아닌 필드 값으로 들어올 경우 jackson에서 처리할 수 있게 하는 역할
 * implement만 해주면된다.
 **/
public interface Constant {
  @JsonValue
  String getType();

}
