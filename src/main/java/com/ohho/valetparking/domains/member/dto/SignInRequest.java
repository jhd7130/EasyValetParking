package com.ohho.valetparking.domains.member.dto;

import com.ohho.valetparking.domains.member.domain.SignIn;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "이메일은 필수입니다.")
    private  String email;
    @NotBlank(message = "비밀번호는 필수입니다.")
    private  String password;
    @NotBlank(message = "부서는 필수입니다.")
    private int department;


    public SignInRequest(String email, String password, int department) {
        this.email = email;
        this.password = password;
        this.department = department;
    }

    public SignIn toSignIn(){
        return SignIn.builder(this).build();
    }
}
