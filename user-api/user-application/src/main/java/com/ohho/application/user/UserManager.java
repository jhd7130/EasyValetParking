package com.ohho.application.user;


import com.ohho.application.user.processor.UserCreateProcessor;
import com.ohho.domain.user.User;
import com.ohho.valetparking.enums.Department;

public class UserManager {

  private UserCreateProcessor userCreateProcessor;

  public UserManager(UserCreateProcessor userCreateProcessor) {
    this.userCreateProcessor = userCreateProcessor;
  }

  public User create(String email, String password, String nickName, Department department) {
    return userCreateProcessor.create(email, password, nickName, department);

  }


}
