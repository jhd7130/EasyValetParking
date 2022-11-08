package com.ohho.infrastructure.user;

import com.ohho.domain.user.User;
import com.ohho.domain.user.UserRepository;
import com.ohho.infrastructure.user.mybatis.UserMapper;

public class UserRepositoryAdaptor implements UserRepository {

 // private final UserMapper userMapper;
  private final UserInMemory userInMemory;

  public UserRepositoryAdaptor( UserInMemory userInMemory) {
//    this.userMapper = userMapper;
    this.userInMemory = userInMemory;
  }

  @Override
  public User save(User user) {
    return userInMemory.save(user);
  }
}
