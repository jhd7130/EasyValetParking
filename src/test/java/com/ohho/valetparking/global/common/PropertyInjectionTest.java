package com.ohho.valetparking.global.common;

import com.ohho.valetparking.ValetparkingApplication;
import com.ohho.valetparking.domains.member.dto.SignInRequest;
import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.global.security.JWTProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.TestPropertySource;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@SpringBootTest
@TestPropertySource(properties = { "spring.config.location=classpath:application.yml" })
public class PropertyInjectionTest {
    private  String SECREAT_KEY;

    private  int ONEWEEK;

    private  int ONEHOUR;

    @Value("${secretKey}")
    public void setSecreatKey(String secreatKey) {
        this.SECREAT_KEY = secreatKey;
    }
    @Value("${refreshtokentime}")
    public void setONEWEEK(int ONEWEEK) {
        this.ONEWEEK = ONEWEEK;
    }
    @Value("${accesstokentime}")
    public void setONEHOUR(int ONEHOUR) {
        this.ONEHOUR = ONEHOUR;
    }
    @Test
    void propertyTest() {

        Assertions.assertEquals("thisiskeyforeasyvaletparkingservicemadebylogan",SECREAT_KEY);
    }

}

