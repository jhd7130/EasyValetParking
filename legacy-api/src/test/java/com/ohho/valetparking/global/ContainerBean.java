package com.ohho.valetparking.global;

import com.ohho.valetparking.ValetparkingApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

/**
 * Role : Responsibility : Cooperation with :
 **/

@SpringBootTest(classes = ValetparkingApplication.class)
public class ContainerBean {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void getBeanListInApplication(){
    if (applicationContext != null) {
      String[] beans = applicationContext.getBeanDefinitionNames();

      for (String bean : beans) {
        System.out.println("bean : " + bean);
      }
    }
  }
}
