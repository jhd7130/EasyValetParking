package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.JoinRequest;
import com.ohho.valetparking.domains.member.domain.dto.SignInResponse;
import com.ohho.valetparking.domains.member.domain.entity.*;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import com.ohho.valetparking.domains.member.repository.VipMapper;
import com.ohho.valetparking.domains.parking.exception.FailTicketRegistrationException;
import com.ohho.valetparking.global.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 2022 08 22 Target : 두개의 mapper를 만들고 분기를 통해 admin과 user 각가의 mapper를 연결할 수 있게 하는 것 Responsibility :
 * Cooperation with :
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

  private final MemberMapper memberMapper;
  private final VipMapper vipMapper;
  private final PasswordEncoder passwordEncoder;

  /**
   * @param joinRequest :: 회원가입에 필요한 속성들을 가지고 있는 DTO역할의 객체, 여기서 값들을 필터링 해줘도 좋을 것 같다.
   * @return boolan  :: 회원 가입이 되었을 경우에는 Ture를 반환하고 실패의 경우에는 예외를 던진다.
   * @throws IllegalStateException    : 객체가 잘못된 상태가 들어와서 회원 가입에 실패한 경우
   * @throws IllegalArgumentException Join 객체의 상태가 잘못들어온 경우
   */
  @Transactional
  public boolean join(JoinRequest joinRequest)
      throws IllegalStateException, IllegalArgumentException {
    log.info("[MemberService] Join() join parameter :: ={}", joinRequest.toString());

    Join join = Join.builder(joinRequest)
        .password(passwordEncoder.encode(joinRequest.getPassword()))
        .build();

    addMember(join);
    return true;
  }

  public void emailValidation(String email) {

    log.info("email = {}", email);
    if (memberMapper.mailCheck(email)) {
      throw new SignUpFailException(ErrorCode.EMAIL_DUPLICATION);
    }
    // 메일 중복이 있을 경우, 커스텀 익셉션 만들어서 날리기
  }

  // token이랑 id 담아서 반환해줍시다. dto 필요하고
  // id 및 부서 조회해오기
  public SignInResponse signIn(SignIn signIn) {
    log.info("[MemberService] ::: sigIn ={}", signIn);
    // 비밀번호 확인에서 id, department 다 가져오기
    SignInResponse signInResponse = passwordMatch(signIn);
    recordSignInHistory(signIn);

    return signInResponse;
  }

  public List<User> getUserList() {
    return memberMapper.userList();
  }


  // -----------------------------------------  sub method  -------------------------------------------------
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

  // admin인지 확인해야함
  public SignInResponse passwordMatch(SignIn signIn) {
    log.info("MemberService :: SignIn :: = {} ", signIn);

    String password = signIn.getPassword();
    // 유저 멤버 통합으로 조회해서 Member 객체로 가져오는 방법으로 변경
    Member member = memberMapper.getMemberByEmail(signIn.getEmail())
        .orElseThrow(() -> new SignInFailException(ErrorCode.EMAIL_NOT_FOUND));

    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new SignInFailException(ErrorCode.NOT_MATCH_PASSWORD);
    }

    return new SignInResponse(member);
  }

  @Transactional
  private void addMember(Join join) {
    log.info("Join ::: join ={}", join);
    int result = 0;

    if (join.isAdmin()) {
      result = memberMapper.newAdminJoin(join);
    } else {
      result = memberMapper.newUserJoin(join);
    }

    if (result != 1) {
      throw new SignUpFailException(ErrorCode.FAIL_REGISTER);
    }
  }

  public List<Vip> getVipByName(String vipName) {
    return memberMapper.getVip(vipName);
  }

  public Long getVipId(String vipName) {
    return vipMapper.getVipId(vipName)
        .orElseThrow(() -> new FailTicketRegistrationException(ErrorCode.NOT_FOUND_VIP));
  }

  public Long getAdminIdByMail(String email) {
    return memberMapper.getAdminId(email)
        .orElseThrow(() -> new FailTicketRegistrationException(ErrorCode.HANDLE_ACCESS_DENIED));
  }
}


