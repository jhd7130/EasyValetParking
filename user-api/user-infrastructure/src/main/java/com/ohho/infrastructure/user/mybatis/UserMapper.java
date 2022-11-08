package com.ohho.infrastructure.user.mybatis;

import com.ohho.domain.user.User;
import java.util.HashMap;
import java.util.UUID;

public class UserMapper {

  private final HashMap<Long, User> hashMap = new HashMap<>();

  public User save(User user) {
    Long id = Long.parseLong(UUID.randomUUID().toString());
    hashMap.put(id, user);
    return hashMap.get(id);
  }
}
