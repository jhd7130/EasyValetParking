package com.ohho.valetparking.domains.member.domain.dto;

import com.ohho.valetparking.domains.member.domain.entity.Member;
import lombok.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SignInResponse {
    private long id;
    private String nickname;
    private String email;
    private int department;

    public SignInResponse(Member member){
        this.id = member.getId();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.department = member.getDepartment();
    }
}
