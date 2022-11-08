package com.ohho.infrastructure.user;

import com.ohho.domain.user.User;
import java.util.HashMap;
import java.util.UUID;

public class UserInMemory {

  public UserInMemory() {
  }

  private final HashMap<String, User> hashMap = new HashMap<>();

  public User save(User user) {
    String id = UUID.randomUUID().toString();
    hashMap.put(id, user);
    return hashMap.get(id);
  }
}
