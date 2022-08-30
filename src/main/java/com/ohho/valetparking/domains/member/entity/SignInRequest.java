package com.ohho.valetparking.domains.member.entity;

import lombok.*;

import java.io.Serializable;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@ToString
@NoArgsConstructor
public class SignInRequest implements Serializable {
    private  String email;
    private  String password;
    private int department;


    public SignInRequest(String email, String password, int department) {
        this.email = email;
        this.password = password;
        this.department = department;
    }
}
