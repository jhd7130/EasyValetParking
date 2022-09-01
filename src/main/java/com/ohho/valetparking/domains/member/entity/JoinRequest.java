

package com.ohho.valetparking.domains.member.entity;

import lombok.*;

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
    private  String nickname;
    private  String email;
    private  String password;
    private  String department;
    private  String company;

}