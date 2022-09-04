package com.ohho.valetparking.domains.member.controller;

import com.ohho.valetparking.domains.member.entity.JoinRequest;
import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.entity.SignInRequest;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.service.MemberService;
import com.ohho.valetparking.global.security.JWTProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    // @RequiredArgsConstructor로 하면 DI가 안된다.
    // 이유 : 롬복의 특성상 @RequiredArgsConstructor는 private final로 선언되어 있는 인스턴스 변수에만 의존성 주입을 해준다.
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity signUp( @RequestBody JoinRequest joinRequest){
            log.info("JOin : ={}" , joinRequest);
            memberService.emailValidation(joinRequest.getEmail());
            memberService.join(joinRequest);
        return ResponseEntity.status(HttpStatus.OK)
                             .body("회원가입 되었습니다.");
    }

    @PostMapping("/member/sign-in")
    public ResponseEntity signIn(@RequestBody SignInRequest signInRequest){
        if(signInRequest.getEmail() == null) throw new SignInFailException("아이디를 입력해주세요.");
        SignIn signIn = SignIn.builder(signInRequest).build();

        memberService.signIn(signIn);

        return ResponseEntity.ok()
                             .header("ACCESSTOKEN",JWTProvider.accessTokenCreate(signIn))
                             .header("REFRESHTOKEN","not yet")
                             .body("로그인에 성공했습니다.");
    }

    @PutMapping("/member/password")
    public ResponseEntity changePassword(){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    /**
     * 요청 형식 : HOST/member/test@test.com
     * @param email
     * @return
     */
    @GetMapping("/member/{email}")
    public ResponseEntity duplicationCheck( @PathVariable("email") String email ){

        log.info("drive in test :: ={}",email);
        memberService.emailValidation(email);

        return ResponseEntity.status(HttpStatus.OK)
                             .body("해당 이메일로 회원가입 가능");
    }



}
