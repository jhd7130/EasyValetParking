

package com.ohho.valetparking.domains.member.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {
    @NotBlank(message = "사내 닉네임을 입력해주세요.")
    private  String nickname;
    @NotBlank(message = "이메일을 입력해주세요.")
    private  String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private  String password;
    @NotBlank(message = "부서를 선택해주세요.")
    private  int department;
    private  String company;

}