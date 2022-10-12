package com.ohho.valetparking.global;

import com.ohho.valetparking.domains.parking.controller.TicketController;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Role : Responsibility : Cooperation with :
 **/
@WebMvcTest(controllers = {TicketController.class}
    , excludeAutoConfiguration = SecurityAutoConfiguration.class
    , excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
public class PermissionCheckTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private JWTProvider tesetJWTProvider;

  private TokenIngredient tokenIngredient;

  @BeforeEach
  void setting() {
    JWTProvider jwtProvider = new JWTProvider();

    ReflectionTestUtils.setField(jwtProvider, "SECREAT_KEY",
        "thisistestsecretkeyformakingtokenbuthavetolongerthen256bitscausethisalgorithmishs256");
    ReflectionTestUtils.setField(jwtProvider, "REFRESHTOKENTIME", "1");
    ReflectionTestUtils.setField(jwtProvider, "ACCESSTOKENTIME", "1");
    this.tesetJWTProvider = jwtProvider;

    tokenIngredient = new TokenIngredient("test@naver.com", 1); // 0번이어야 관리자
  }

  @Test
  @DisplayName("사용자의 관리자 권한 기능 접근시 실패")
  void failTest() {
    // situation : permission annotation이 적용된 Controller endpoint로 일반 사용자가 접근할때 문제 발생
    // 재료 : 일반 사용자의 토큰과 @ReqierPermission이 있는 endPoint
    // given

    // when

    // then
  }

}
