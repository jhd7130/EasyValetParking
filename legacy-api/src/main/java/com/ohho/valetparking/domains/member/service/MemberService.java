package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.JoinRequest;
import com.ohho.valetparking.domains.member.domain.entity.*;
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
    emailValidation(joinRequest.getEmail());

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



  public List<User> getUserList() {
    return memberMapper.userList();
  }


  // -----------------------------------------  sub method  -------------------------------------------------

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


