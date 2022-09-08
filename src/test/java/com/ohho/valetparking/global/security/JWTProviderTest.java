package com.ohho.valetparking.global.security;

import com.ohho.valetparking.domains.member.domain.SignIn;
import com.ohho.valetparking.domains.member.dto.SignInRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class JWTProviderTest {
    @Test
    void 토큰_생성_성공_테스트() throws InterruptedException {
         // given
        final SignIn sign = SignIn.builder(new SignInRequest("test3@maver.com","test1234",1))
                .build();
        String token = JWTProvider.accessTokenCreate(sign);
        assertThat(token).isNotNull();
    }

    @Test
    void 토큰_생성후_유효성테스트(){
    // given
        final SignIn sign = SignIn.builder(new SignInRequest("test3@maver.com","test1234",1))
                .build();
        final String token = JWTProvider.accessTokenCreate(sign);
    // when
        assertThat(JWTProvider.isValid(token))
                  .isTrue();
    }

}
