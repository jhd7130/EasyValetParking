package com.ohho.presentation.user;

import com.ohho.application.user.UserManager;
import com.ohho.domain.user.User;
import com.ohho.presentation.user.request.CreateUserRequest;
import com.ohho.valetparking.response.ApiResponseWrapper;
import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ApiResponseWrapper<User> create(@RequestBody @Valid CreateUserRequest request) {
    User user = userManager.create(request.getEmail(), request.getPassword(), request.getNickname(),
                        request.getDepartment());
    return ApiResponseWrapper.success(user);
  }

}
