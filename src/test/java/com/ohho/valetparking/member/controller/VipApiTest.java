package com.ohho.valetparking.member.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.domains.member.controller.VipApi;
import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.enums.VipType;
import com.ohho.valetparking.domains.member.service.VipService;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 컨트롤러에서 interceptor를 제외한 컨트롤러 테스트
 **/
@WebMvcTest(controllers = {
    VipApi.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfigurer.class)})
public class VipApiTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private VipService vipService;

  @MockBean
  private JWTProvider jwtProvider; // interceptor을 빈으로 만들 때 필요

  @Autowired
  private ObjectMapper objectMapper;

  @Test
  @DisplayName("브이아이피 지정조회 숫자 조회 동작 테스트")
  void vipApiByNameTest() throws Exception {
    // given
    mockMvc.perform(MockMvcRequestBuilders.get("/vip/1")).andExpect(status().isOk()).andDo(print());
  }


  @Test
  @DisplayName("브이아이피 지정조회 차량번호 조회 동작 테스트")
  void vipApiByNameEnTest() throws Exception {
    // given
    mockMvc.perform(MockMvcRequestBuilders.get("/vip/car-number/2345")).andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @DisplayName("브이아이피 지정조회 한글 조회 동작 테스트")
  void vipApiByNameKoTest() throws Exception {
    // given
    mockMvc.perform(MockMvcRequestBuilders.get("/vip/name/홍길동")).andExpect(status().isOk())
        .andDo(print());
  }

  @Test
  @DisplayName("브이아이피 등록 동작 테스트 enum")
  void registorVipEnum() throws Exception {
    String json = objectMapper.writeValueAsString(new VipRequest("홍길동", "2345", VipType.HDC, " "));
    //when
    mockMvc.perform(
            MockMvcRequestBuilders.post("/vip").contentType(MediaType.APPLICATION_JSON).content(json))
              .andExpect(status().isOk()).andDo(print());
  }
@Test
  @DisplayName("브이아이피 등록 동작 테스트 숫자")
  void registorVipNum() throws Exception {
    String json = "{\"name\":\"홍길동\",\"car_number\":\"2345\",\"type\":1,\"uniqueness\":\" \"}";
    //when
    mockMvc.perform(
            MockMvcRequestBuilders.post("/vip").contentType(MediaType.APPLICATION_JSON).content(json))
              .andExpect(status().isOk()).andDo(print());
  }

}
