package com.ohho.valetparking.member.service;

import com.ohho.valetparking.domains.member.domain.dto.JoinRequest;
import com.ohho.valetparking.domains.member.domain.dto.SignInRequest;
import com.ohho.valetparking.domains.member.domain.dto.SignInResponse;
import com.ohho.valetparking.domains.member.domain.entity.Member;
import com.ohho.valetparking.domains.member.domain.entity.SignIn;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import com.ohho.valetparking.domains.member.repository.VipMapper;
import com.ohho.valetparking.domains.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    MemberService memberService;


    @Test
    @DisplayName("회원가입 기능 테스트")
    void 회원가입() {
        // 테스트 실패 이유 못찾음
        // given
        JoinRequest joinRequest = new JoinRequest("1","1","1",1,"1");
        // when
        when(memberService.join(joinRequest)).thenReturn(true);
        // then
        assertThat(memberService.join(joinRequest)).isTrue();
    }

    @Test
    void 비밀번호_암호화_테스트() {
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //when
        String test = passwordEncoder.encode("test1234");
        //then
        assertThat(passwordEncoder.matches("test1234",test));
    }
    @Test
    void 로그인반환객체_컨버팅_테스트(){
        // given
        Member member = new Member(1L,"홍길동","test@naver.com","testpassword",0);
        SignInResponse sign = new SignInResponse(1L,"홍길동","test@naver.com",0);
        // when // then
        assertThat(new SignInResponse(member)).isEqualTo(sign);

    }
    @Test
    void 로그인테스트(){
        // given
        SignInRequest sign = new SignInRequest("test@naver.com","testpassword");
        Member member = new Member(1L,"홍길동","test@naver.com","testpassword",0);
        SignInResponse signInResponse = new SignInResponse(1L,"홍길동","test@naver.com",0);
        SignInResponse signInResponseTest = new SignInResponse(member);

        // when
        when(memberService.passwordMatch(any(SignIn.class))).thenReturn(signInResponse);
        // then
        assertEquals(signInResponseTest,memberService.passwordMatch(sign.toSignIn()));
    }




}
