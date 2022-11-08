package com.ohho.presentation.user.request;

import com.ohho.domain.user.Department;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class CreateUserRequest {
  @NotNull
  @Email
  private String email;
  @NotNull
  private String password;
  @NotNull
  private String nickname;
  @NotNull
  private Department department;

  public CreateUserRequest() {
  }

  public CreateUserRequest(String email, String password, String nickname, Department department) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.department = department;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getNickname() {
    return nickname;
  }

  public Department getDepartment() {
    return department;
  }
}
