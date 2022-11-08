package com.ohho.application.user.processor;

import com.ohho.domain.user.Department;
import com.ohho.domain.user.User;
import com.ohho.domain.user.UserRepository;

public class UserCreateProcessor {

  private UserRepository userRepository;

  public UserCreateProcessor(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(String email, String password, String nickName, Department department) {
    User user = User.from(email, password, nickName, department);
    return userRepository.save(user);
  }

}
