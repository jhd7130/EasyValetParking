package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.entity.LoginHistory;
import com.ohho.valetparking.domains.member.domain.entity.Member;
import com.ohho.valetparking.domains.member.domain.entity.SignIn;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import com.ohho.valetparking.global.util.SessionUtil;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class JwtLoginService implements LoginService {

  private final MemberMapper memberMapper;
  private final PasswordEncoder passwordEncoder;
  private final HttpSession httpSession;
  private final JWTProvider jwtProvider;

  @Override
  public Member signIn(SignIn signIn, HttpServletResponse response) {
    log.info("[MemberService] ::: sigIn ={}", signIn);
    // 비밀번호 확인에서 id, department 다 가져오기
    Member member = passwordMatch(signIn);
    recordSignInHistory(signIn);

    TokenIngredient tokenIngredient = new TokenIngredient(member.getEmail(),
        member.getDepartment());

    // Token 적용시키기
    response.addHeader("ACCESSTOKEN", jwtProvider.accessTokenCreate(tokenIngredient));
    SessionUtil.setRefreshtoken(httpSession,jwtProvider.refreshTokenCreate(tokenIngredient));

    return member;
  }

  @Override
  public void logOut() {

  }

  public Member passwordMatch(SignIn signIn) {
    log.info("MemberService :: SignIn :: = {} ", signIn);

    String password = signIn.getPassword();
    // 유저 멤버 통합으로 조회해서 Member 객체로 가져오는 방법으로 변경
    Member member = memberMapper.getMemberByEmail(signIn.getEmail())
        .orElseThrow(() -> new SignInFailException(ErrorCode.EMAIL_NOT_FOUND));

    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new SignInFailException(ErrorCode.NOT_MATCH_PASSWORD);
    }

    return member;
  }

  @Transactional
  private void recordSignInHistory(SignIn signIn) {
    log.info("signIn ={}", signIn);
    int adminFlag = 0;
    long memberId = memberMapper.getAdminId(signIn.getEmail())
        .orElseGet(() -> memberMapper.getMemberId(signIn.getEmail())
            .orElseThrow(() -> new SignInFailException(ErrorCode.EMAIL_NOT_FOUND)));

    // 그냥 관리자를 가져오자
    if (!memberMapper.isAdmin(signIn.getEmail())) {
      adminFlag = 1;
    }

    LoginHistory loginHistory = LoginHistory.from(memberId, adminFlag); // 관리자인지 아닌지만 체크 위에 로지을 변경

    memberMapper.recordSignInHistory(loginHistory);
  }
}
