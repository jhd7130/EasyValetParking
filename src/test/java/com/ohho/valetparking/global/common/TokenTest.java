package com.ohho.valetparking.global.common;

import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

@ExtendWith(MockitoExtension.class)
public class TokenTest {

    JWTProvider tesetJWTProvider;
    String token;

    @BeforeEach
    @DisplayName("spring 없이 상태 주입")
    void settingToken(){
        JWTProvider jwtProvider = new JWTProvider();
        ReflectionTestUtils.setField(jwtProvider,"SECREAT_KEY","thisistestsecretkeyformakingtokenbuthavetolongerthen256bitscausethisalgorithmishs256");
        ReflectionTestUtils.setField(jwtProvider,"REFRESHTOKENTIME","1");
        ReflectionTestUtils.setField(jwtProvider,"ACCESSTOKENTIME","1");
        this.tesetJWTProvider = jwtProvider;

        this.token = tesetJWTProvider.accessTokenCreate(new TokenIngredient("test@maver.com",0));
    }

    @Test
    @DisplayName("엑세스토큰 생성 테스트 환경변수를 가져오는 @Value를 web mvc의 작동 없이 테스트")
    void makingAccessToken() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        String token = tesetJWTProvider.accessTokenCreate(new TokenIngredient("test@maver.com",0));
        assertAll(
                ()->  assertThat(tesetJWTProvider.getACCESSTOKENTIME()).isEqualTo("1"),
                ()-> assertThat(tesetJWTProvider.getREFRESHTOKENTIME()).isEqualTo("1"),
                ()-> assertThat(tesetJWTProvider.getSECREAT_KEY()).isEqualTo("thisistestsecretkeyformakingtokenbuthavetolongerthen256bitscausethisalgorithmishs256"),
                ()-> assertTrue(tesetJWTProvider.isValid(token))
        );

    }

    @Test
    @DisplayName("claims에서 유효시간 가져와서 현재 시간과 비교")
    void getTokenClaims(){
        boolean flag = tesetJWTProvider.isValid(token);
        assertTrue(flag);
    }

    @Test
    @DisplayName("토큰에서 객체 꺼내오기")
    void getTokenIngredient(){
        assertAll(
                () -> assertThat(tesetJWTProvider.getEmailInFromToken(token)).isEqualTo("test@maver.com"),
                () -> assertThat(tesetJWTProvider.getDepartmentInFromToken(token)).isEqualTo(0),
                () -> assertThat(tesetJWTProvider.getTokenIngredientFromToken(token)).isEqualTo(new TokenIngredient("test@maver.com",0))
        );

    }
}
