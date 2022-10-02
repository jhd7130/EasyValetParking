package com.ohho.valetparking.domains.member.domain.entity;

import com.ohho.valetparking.domains.member.domain.dto.SignInResponse;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@AllArgsConstructor
@ToString
@Getter
@EqualsAndHashCode
public class Member {

    private final long id;
    private final String nickname;
    private final String email;
    private final String password;
    private final int department;

}
