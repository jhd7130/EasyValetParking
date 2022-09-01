package com.ohho.valetparking.global.security;

import com.ohho.valetparking.domains.member.entity.JoinRequest;
import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.entity.SignInRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class JWTProviderTest {

    private SignIn sign;

    @BeforeEach
    void 테스트_베이스_세팅(){
        sign = SignIn.builder(new SignInRequest("test3@maver.com","test1234",1))
                     .build();
    }

    @Test
    void 토큰_생성_성공_테스트() throws InterruptedException {
         // given
        String token = JWTProvider.accessTokenCreate(sign);
        assertThat(token).isNotNull();
    }

    @Test
    void 토큰_생성후_유효성테스트(){
    // given
        String token = JWTProvider.accessTokenCreate(sign);
    // when
        assertThat(JWTProvider.isValid(token))
                  .isTrue();
    }

}
