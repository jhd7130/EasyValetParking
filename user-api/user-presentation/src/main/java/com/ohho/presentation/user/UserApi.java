package com.ohho.presentation.user;

import com.ohho.application.user.UserManager;
import com.ohho.domain.user.User;
import com.ohho.presentation.user.request.CreateUserRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class UserApi {

  private UserManager userManager;

  public UserApi(UserManager userManager) {
    this.userManager = userManager;
  }

  @PostMapping("/create")
  public ResponseEntity<User> create(@RequestBody @Valid CreateUserRequest request) {
    User user = userManager.create(request.getEmail(), request.getPassword(), request.getNickname(),
                        request.getDepartment());
    return ResponseEntity.ok(user);
  }

}
