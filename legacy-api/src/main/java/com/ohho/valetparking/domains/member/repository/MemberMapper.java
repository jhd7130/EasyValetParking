package com.ohho.valetparking.domains.member.repository;

import com.ohho.valetparking.domains.member.domain.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Mapper
public interface MemberMapper {

  // Create
  int newUserJoin(Join join);

  int newAdminJoin(Join join);

  void recordSignInHistory(LoginHistory loginHistory);

  // Validation
  boolean mailCheck(String email);

  boolean isAdmin(String email);

  // Find&Get
  Optional<String> getPassword(String email);

  List<User> userList();

  Optional<Long> getMemberId(String email);

  Optional<Long> getAdminId(String email);

  List<Vip> getVip(String vipName);

  Optional<Member> getMemberByEmail(String email);
}
