package com.ohho.application.user.facade;

import com.ohho.domain.user.Department;

public class CreateUser {
  private final String email;
  private final String password;
  private final String nickName;
  private final Department department;

  public CreateUser(String email, String password, String nickName, Department department) {
    this.email = email;
    this.password = password;
    this.nickName = nickName;
    this.department = department;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getNickName() {
    return nickName;
  }

  public Department getDepartment() {
    return department;
  }
}
