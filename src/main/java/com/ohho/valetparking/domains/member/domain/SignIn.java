package com.ohho.valetparking.domains.member.domain;

import com.ohho.valetparking.domains.member.dto.SignInRequest;
import lombok.Getter;
import lombok.ToString;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@ToString
public class SignIn {
    private final  String email;
    private final  String password;
    // 0이 사용자 1이 관리자
    private final int department;

    private SignIn(SignInBuilder signInBuilder) {
        this.email = signInBuilder.email;
        this.password = signInBuilder.password;
        this.department = signInBuilder.department;
    }
    public static SignInBuilder builder(SignInRequest signInRequest){
        return new SignInBuilder(signInRequest);
    }

    public static class SignInBuilder{
        private final  String email;
        private final  String password;
        private final int department;

        public SignInBuilder(SignInRequest signInRequest) {
            this.email = signInRequest.getEmail();
            this.password = signInRequest.getPassword();
            this.department = signInRequest.getDepartment();
        }

        public SignIn build(){
            return new SignIn(this);
        }
    }

    public boolean isAdmin(){
        return this.department == 1;
    }

}
