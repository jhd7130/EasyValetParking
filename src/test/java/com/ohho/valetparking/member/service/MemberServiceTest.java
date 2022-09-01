package com.ohho.valetparking.member.service;

import com.ohho.valetparking.domains.member.entity.Join;
import com.ohho.valetparking.domains.member.entity.JoinRequest;
import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.entity.SignInRequest;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import com.ohho.valetparking.domains.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
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

    @Mock
    MemberMapper memberMapper;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 기능 테스트")
    void 회원가입() {
        // 테스트 실패 이유 못찾음
        // given
        JoinRequest joinRequest = new JoinRequest("1","1","1","1","1");
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
    void 비밀번호_매칭테스트(){
        //given
        SignIn sign = SignIn.builder(new SignInRequest("test3@maver.com","test1234",1))
                            .build();

        //when
        when(memberService.passwordMatch(sign)).thenReturn(true);

        //then
        assertThat(memberService.passwordMatch(sign)).isTrue();
    }



}
