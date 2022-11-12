package com.ohho.domain.user;

import com.ohho.valetparking.enums.Department;
import java.time.LocalDateTime;
import java.util.Objects;

public class User {

  protected User() {
  }

  private Long id;

  private String email;

  private String nickName;

  private String password;

  private Department department;

  private LocalDateTime createAt;

  private LocalDateTime lastUpdateAt;

  private LocalDateTime deleteAt;

  private LocalDateTime confirmedAt;

  private boolean activate;

  private User(String email, String nickName, String password, Department department) {

    this.email = email;
    this.nickName = nickName;
    this.password = password;
    this.department = department;
    this.createAt = LocalDateTime.now();
    this.activate = false;

  }
  public static User from(String email, String nickName, String password,
      Department department) {

    return new User(email, nickName, password, department);

  }

  public void confirm() {
    this.confirmedAt = LocalDateTime.now();
    this.activate = true;
  }

  public void delete() {
    this.deleteAt = LocalDateTime.now();
    this.activate = false;
  }

  public boolean isAdmin() {
    return this.department == Department.FS;
  }












  public Long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public String getNickName() {
    return nickName;
  }

  public String getPassword() {
    return password;
  }

  public Department getDepartment() {
    return department;
  }

  public LocalDateTime getCreateAt() {
    return createAt;
  }

  public LocalDateTime getLastUpdateAt() {
    return lastUpdateAt;
  }

  public boolean isActivate() {
    return activate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    return activate == user.activate && Objects.equals(id, user.id)
        && Objects.equals(email, user.email) && Objects.equals(nickName,
        user.nickName) && Objects.equals(password, user.password)
        && department == user.department && Objects.equals(createAt, user.createAt)
        && Objects.equals(lastUpdateAt, user.lastUpdateAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, email, nickName, password, department, createAt, lastUpdateAt,
        activate);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", email='" + email + '\'' +
        ", nickName='" + nickName + '\'' +
        ", password='" + password + '\'' +
        ", department=" + department +
        ", createAt=" + createAt +
        ", lastUpdateAt=" + lastUpdateAt +
        ", activate=" + activate +
        '}';
  }
}
