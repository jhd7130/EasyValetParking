package com.ohho.valetparking.domains.member.controller;

import com.ohho.valetparking.domains.member.domain.entity.Member;
import com.ohho.valetparking.domains.member.domain.dto.JoinRequest;
import com.ohho.valetparking.domains.member.domain.dto.SignInRequest;
import com.ohho.valetparking.domains.member.domain.entity.User;
import com.ohho.valetparking.domains.member.enums.MemberType;
import com.ohho.valetparking.domains.member.service.LoginService;
import com.ohho.valetparking.domains.member.service.MemberService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.permission.PermissionRequired;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberController {

  // 롬복의 특성상 @RequiredArgsConstructor는 private final로 선언되어 있는 인스턴스 변수에만 의존성 주입을 해준다.
  private final MemberService memberService;
  private final LoginService loginService;

  @PostMapping(value = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse signUp(@RequestBody @Valid JoinRequest joinRequest) {
    log.info("JOin : ={}", joinRequest);

    memberService.join(joinRequest);

    return ApiResponse.success("회원가입되었습니다.");

  }

  @PostMapping(value = "/member/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse signIn(@RequestBody SignInRequest signInRequest,
      HttpServletResponse response, HttpServletRequest request) {

    Member member = loginService.signIn(signInRequest.toSignIn(), response);

    return ApiResponse.success(member);
  }

  @PostMapping(value = "/member/sign-out", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse signout() {
    loginService.signOut();

    return ApiResponse.success("로그아웃 성공");
  }

  @PutMapping(value = "/member/password", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse changePassword() {

    return ApiResponse.success("성공하였습니다.");

  }

  @GetMapping(value = "/member/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse duplicationCheck(@PathVariable("email") @Email @NotBlank String email) {

    log.info("drive in test :: ={}", email);
    memberService.emailValidation(email);

    return ApiResponse.success("회원가입가능한 이메일입니다.");
  }

  /**
   * 사용자 조회(관리자 조회랑은 다른다.)
   *
   * @return
   */
  @PermissionRequired(permission = MemberType.ADMIN)
  @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse userList() {
    log.info("[MemberController] userList :::: entracing success");

    List<User> userList = memberService.getUserList();

    return ApiResponse.success(userList);
  }


}
