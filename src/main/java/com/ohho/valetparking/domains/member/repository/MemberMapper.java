package com.ohho.valetparking.domains.member.repository;

import com.ohho.valetparking.domains.member.entity.User;
import com.ohho.valetparking.domains.member.entity.Join;
import com.ohho.valetparking.domains.member.entity.LoginHistory;
import com.ohho.valetparking.domains.member.entity.Vip;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Mapper
public interface MemberMapper {

    int newUserJoin(Join join);
    int newAdminJoin(Join join);
    boolean mailCheck(String email);
    Optional<String> getPassword(String email);

    void recordSignInHistory(LoginHistory loginHistory);
    List<User> userList();
    Optional<Long> getMemberId(String email);
    Optional<Long> getAdminId(String email);
    boolean isAdmin(String email);
    List<Vip> getVip(String vipName);
}
