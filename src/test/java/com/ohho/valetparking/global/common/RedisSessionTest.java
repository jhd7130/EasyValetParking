package com.ohho.valetparking.global.common;

import com.ohho.valetparking.ValetparkingApplication;
import com.ohho.valetparking.domains.member.domain.entity.Member;
import com.ohho.valetparking.global.properties.RedisSessionProperty;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.TestPropertySource;

/**
 * Role : Responsibility : Cooperation with :
 **/
@SpringBootTest(classes = ValetparkingApplication.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
public class RedisSessionTest {
//  @Autowired
//  private RedisSessionProperty redisSessionProperty;
//
//  @Test
//  @DisplayName("property 주입 확인 테스트")
//  void test(){
//    Assertions.assertThat(6001).isEqualTo(redisSessionProperty.getPort());
//  }
//
//
//  @Test
//  @DisplayName("HttpSession 사용시 레디스 저장 여부 테스트")
//  void sessionUsingTest(){
//  // given
//    MockHttpServletRequest request = new MockHttpServletRequest();
//  // when
//    HttpSession httpSession = request.getSession();
//    Member member1 = new Member(5L,"test","test","test",0);
//    httpSession.setAttribute("Test",member1);
//  // then
//    Member member = (Member) httpSession.getAttribute("Test");
//    Assertions.assertThat(member1).isEqualTo(member);
//  }
//


}
