package com.ohho.valetparking.global;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.domains.member.controller.VipApi;
import com.ohho.valetparking.domains.member.service.VipService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.ApiResponse.ErrorBody;
import com.ohho.valetparking.global.configuration.InterceptorConfiguration;
import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.error.exception.BaseException;
import com.ohho.valetparking.global.error.exception.InvalidArgumentException;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import java.util.Collection;
import java.util.Objects;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Role : Responsibility : Cooperation with :
 **/
@WebMvcTest(controllers = {VipApi.class}
    , excludeAutoConfiguration = SecurityAutoConfiguration.class
    , excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = InterceptorConfiguration.class)})
@ExtendWith(MockitoExtension.class)
public class ExceptionTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  VipService vipService;

  @MockBean
  JWTProvider jwtProvider;

  @MockBean
  VipApi vipApi;

  @Test
  @DisplayName("BaseException 메세지 테스트입니다.")
  void baseExceptionMessageTest() {
    // given
    BaseException baseException = new BaseException(ErrorCode.INVALID_ARGUMENT);
    // when
    assertAll(
        () -> assertThat("잘못된 입력값입니다.").isEqualTo(baseException.getMessage()),
        () -> assertThat("잘못된 입력값입니다.").isEqualTo(baseException.getErrorCode().getMessage()),
        () -> assertThat("C001").isEqualTo(baseException.getErrorCode().getCode()),
        () -> assertThat(400).isEqualTo(baseException.getErrorCode().getStatus())
    );
  }

  @Test
  @DisplayName("BaseException을 확장한 예외 객체 테스트입니다. ErrorCode만 이용한 경우")
  void ExtnedsbaseExceptionMessageTest() {
    // given
    BaseException baseException = new InvalidArgumentException(ErrorCode.INVALID_ARGUMENT);
    // when
    assertAll(
        () -> assertThat("잘못된 입력값입니다.").isEqualTo(baseException.getMessage()),
        () -> assertThat("잘못된 입력값입니다.").isEqualTo(baseException.getErrorCode().getMessage()),
        () -> assertThat("C001").isEqualTo(baseException.getErrorCode().getCode()),
        () -> assertThat(400).isEqualTo(baseException.getErrorCode().getStatus())
    );
  }

  @Test
  @DisplayName("반환 api 예외 발생 테스트. 직접 입력한 메세지를 입력하는 경우")
  void apiResponseWithExceptionObjectTest() {
    // given
    BaseException baseException = new InvalidArgumentException("테스트입니다.",ErrorCode.INVALID_ARGUMENT);
    ApiResponse apiResponse = ApiResponse.fail(baseException.getErrorCode(),baseException.getMessage());

    // when
    ErrorBody errorBody = (ErrorBody) apiResponse.getData();

    // then
    assertAll(
        () -> assertThat("테스트입니다.").isEqualTo(errorBody.getMessage()),
        () -> assertThat("C001").isEqualTo(errorBody.getCode()),
        () -> assertThat(400).isEqualTo(errorBody.getStatus())
    );
  }
}
