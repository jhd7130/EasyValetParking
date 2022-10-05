package com.ohho.valetparking.domains.member.controller;

import com.ohho.valetparking.domains.member.domain.dto.SignInResponse;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import com.ohho.valetparking.domains.member.domain.dto.JoinRequest;
import com.ohho.valetparking.domains.member.domain.dto.SignInRequest;
import com.ohho.valetparking.domains.member.domain.entity.User;
import com.ohho.valetparking.domains.member.service.MemberService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Slf4j
@Validated
public class MemberController {
    // @RequiredArgsConstructor로 하면 DI가 안된다.
    // 이유 : 롬복의 특성상 @RequiredArgsConstructor는 private final로 선언되어 있는 인스턴스 변수에만 의존성 주입을 해준다.
    private final MemberService memberService;
    private final JWTProvider jwtProvider;

    @PostMapping(value = "/member", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signUp( @RequestBody @Valid JoinRequest joinRequest ){
            log.info("JOin : ={}" , joinRequest);

            memberService.emailValidation(joinRequest.getEmail());
            memberService.join(joinRequest);
            
        return ResponseEntity.status(HttpStatus.OK)
                             .body("회원가입 되었습니다.");

    }

    @PostMapping(value = "/member/sign-in", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity signIn(@RequestBody SignInRequest signInRequest){

        SignInResponse signInDTO = memberService.signIn(signInRequest.toSignIn());
        TokenIngredient tokenIngredient = new TokenIngredient(signInDTO.getEmail(),signInDTO.getDepartment());

        return ResponseEntity.ok()
                             .header("ACCESSTOKEN",jwtProvider.accessTokenCreate(tokenIngredient))
                             .header("REFRESHTOKEN","not yet")
                             .body(SuccessResponse.success(signInDTO));
    }

    @PutMapping(value = "/member/password", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity changePassword(){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    /**
     * 요청 형식 : HOST/member/test@test.com
     * @param email
     */
    @GetMapping(value = "/member/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity duplicationCheck( @PathVariable("email") @Email @NotBlank String email ){

        log.info("drive in test :: ={}",email);
        memberService.emailValidation(email);

        return ResponseEntity.status(HttpStatus.OK)
                             .body("해당 이메일로 회원가입 가능");
    }

    /**
     * 사용자 조회(관리자 조회랑은 다른다.)
     * @return
     */
    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity userList(){
        log.info("[MemberController] userList :::: entracing success");

        List<User> userList = memberService.getUserList();

        return ResponseEntity.status(HttpStatus.OK)
                             .body(userList);
    }

    @GetMapping(value = "/vip/{vipName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Vip>> getVip(@PathVariable String vipName ) {
        log.info("[MemberController] :::: vipName = {}",vipName);

        memberService.getVipByName(vipName);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(memberService.getVipByName(vipName));
    }
}
