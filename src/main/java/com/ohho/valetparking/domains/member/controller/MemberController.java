package com.ohho.valetparking.domains.member.controller;

import com.ohho.valetparking.domains.member.entity.Join;
import com.ohho.valetparking.domains.member.entity.JoinRequest;
import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.entity.SignInRequest;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.service.MemberService;
import com.ohho.valetparking.global.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    // @RequiredArgsConstructor로 하면 DI가 안된다.
    // 이유 : 롬복의 특성상 @RequiredArgsConstructor는 private final로 선언되어 있는 인스턴스 변수에만 의존성 주입을 해준다.
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity signUp( @RequestBody JoinRequest joinRequest){
            memberService.join(joinRequest);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/member/sign-in")
    public ResponseEntity signIn(@RequestBody SignInRequest signInRequest){
        SignIn signIn = SignIn.builder(signInRequest).build();
        memberService.signIn(signIn);

        return ResponseEntity.ok()
                             .header("ACCESSTOKEN",JWTProvider.accessTokenCreate(signIn))
                             .header("REFRESHTOKEN","미완성")
                             .build();
    }

    @PutMapping("/member/password")
    public ResponseEntity changePassword(){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * 요청 형식 :  domain/member/test@test.com
     * @param email
     * @return
     */

    @GetMapping("/member/{email}")
    public ResponseEntity duplicationCheck( @PathVariable("email") String email ){

        log.info("drive in test :: ={}",email);
        memberService.emailValidation(email);

        return new ResponseEntity("해당 이메일로 회원가입 가능", HttpStatus.OK);
    }

    // 메일 인증 절차



}
