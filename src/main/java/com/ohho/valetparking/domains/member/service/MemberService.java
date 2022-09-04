package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.entity.*;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 2022 08 22
 * Target : 두개의 mapper를 만들고 분기를 통해 admin과 user 각가의 mapper를 연결할 수 있게 하는 것
 * Responsibility :
 * Cooperation with :
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * @param joinRequest :: 회원가입에 필요한 속성들을 가지고 있는 DTO역할의 객체, 여기서 값들을 필터링 해줘도 좋을 것 같다.
     * @return boolan  :: 회원 가입이 되었을 경우에는 Ture를 반환하고 실패의 경우에는 예외를 던진다.
     * @throws IllegalStateException : 객체가 잘못된 상태가 들어와서 회원 가입에 실패한 경우
     * @throws IllegalArgumentException Join 객체의 상태가 잘못들어온 경우
     */
    @Transactional
    public boolean join(JoinRequest joinRequest) throws IllegalStateException,IllegalArgumentException {
        log.info("[MemberService] Join() join parameter :: ={}",joinRequest.toString());

        Join join = Join.builder(joinRequest)
                        .password(passwordEncoder.encode(joinRequest.getPassword()))
                        .build();

        addMember(join);
        return true;
    }

    public void emailValidation(String email){
        log.info("email = {}",email);
        if(memberMapper.mailCheck(email)) throw new SignUpFailException("이메일 중복");
        // 메일 중복이 있을 경우, 커스텀 익셉션 만들어서 날리기
    }

    public void signIn(SignIn signIn) {
        // 로그인
        passwordMatch(signIn);
        // 회원의 email로 id를 조회하고 로그인 기록 테이블에 기록 남긴다.
        recordSignIn(signIn);
    }




    // -----------------------------------------  sub method  -------------------------------------------------
    @Transactional
    private void recordSignIn(SignIn signIn) {
        log.info("signIn ={}" , signIn);
        long id = memberMapper.getMemberId(signIn.getEmail());
        LoginHistory loginHistory = new LoginHistory(id,signIn.getDepartment());

        if(signIn.isAdmin()){
            memberMapper.recordSignInHistory(loginHistory);
        }

        if(!signIn.isAdmin()) {
            memberMapper.recordSignInHistory(loginHistory);
        }

    }


    public boolean passwordMatch(SignIn signIn) {
        log.info("MemberService :: SignIn :: = {} ",signIn);
        String password= signIn.getPassword();
        String passwordFormDb = memberMapper.getPassword(signIn.getEmail());

        if(passwordFormDb == null) throw new SignInFailException("이메일을 다시 입력해주세요.");

        if(!passwordEncoder.matches(password,passwordFormDb)){
            throw new SignInFailException("비밀번호를 다시 입력해주세요.");
        }

        return true;
    }

    @Transactional
    private void addMember(Join join) {
        int result = 0;
        if ( join.isAdmin() ) {
            result = memberMapper.newAdminJoin(join);
        }else {
            result = memberMapper.newUserJoin(join);
        }

        if(result != 1){
            throw new SignUpFailException("회원가입에 실패했습니다.");
        }

    }
}


