package com.ohho.valetparking.domains.member.repository;

import com.ohho.valetparking.domains.member.entity.Join;
import com.ohho.valetparking.domains.member.entity.SignIn;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
    String getPassword(String email);

    long getMemberId(String email);

    void recordSignInHistory(int department, long email);
}
