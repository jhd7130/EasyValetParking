package com.ohho.valetparking.global.util;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 우선 application.yml 파일 그대로 쓸 수 있게 SpringbootTest
 */
@SpringBootTest
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public class JWTProviderTest {

  @Autowired
  JWTProvider jwtProvider;

  String accessToken;
  String refreshToken;

  @BeforeAll
  @DisplayName("테스트를 위한 인자 값 설정")
  void settingTest() {
    String time = jwtProvider.getACCESSTOKENTIME();
    assertThat(time).isEqualTo("1");
  }

  @BeforeAll
  @DisplayName("관리자 token 생성 테스트 및 두개의 토큰 차이 확인 ")
  void checkTokenCreateAndCompare() {
    // when
    TokenIngredient tokenIngredient = new TokenIngredient("test@naver.com", 0);

    //then
    accessToken = jwtProvider.accessTokenCreate(tokenIngredient);
    refreshToken = jwtProvider.refreshTokenCreate(tokenIngredient);

    // result
    assertAll(
        () -> assertThat(accessToken).isNotNull(),
        () -> assertThat(refreshToken).isNotNull(),
        () -> assertThat(accessToken).isNotEqualTo(refreshToken)
    );
  }

  @Test
  @DisplayName("테스트를 위한 ")
  void test(){
  // given

  // when

  // then
  }



}
