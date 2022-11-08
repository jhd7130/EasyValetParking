package com.ohho.application.user;


import com.ohho.application.user.processor.UserCreateProcessor;
import com.ohho.domain.user.Department;
import com.ohho.domain.user.User;

public class UserManager {

  private UserCreateProcessor userCreateProcessor;

  public UserManager(UserCreateProcessor userCreateProcessor) {
    this.userCreateProcessor = userCreateProcessor;
  }

  public User create(String email, String password, String nickName, Department department) {
    return userCreateProcessor.create(email, password, nickName, department);

  }


}
