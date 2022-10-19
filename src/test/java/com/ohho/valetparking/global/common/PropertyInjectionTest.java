package com.ohho.valetparking.global.common;

import com.ohho.valetparking.ValetparkingApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


@SpringBootTest
public class PropertyInjectionTest {

  private String SECREAT_KEY;
  private int ONEWEEK;
  private int ONEHOUR;

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

    Assertions.assertEquals("thisiskeyforeasyvaletparkingservicemadebylogan", SECREAT_KEY);
  }

}

