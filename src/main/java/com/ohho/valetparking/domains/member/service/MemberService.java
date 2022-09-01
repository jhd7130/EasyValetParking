package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.entity.Join;
import com.ohho.valetparking.domains.member.entity.JoinRequest;
import com.ohho.valetparking.domains.member.entity.SignIn;
import com.ohho.valetparking.domains.member.entity.SignInRequest;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;

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

    public boolean emailValidation(String email){

        // 메일 중복이 있을 경우, 커스텀 익셉션 만들어서 날리기
        return memberMapper.mailCheck(email);

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

        long id = memberMapper.getMemberId(signIn.getEmail());

        if(signIn.isAdmin()){
            memberMapper.recordSignInHistory(1,id);
        }

        memberMapper.recordSignInHistory(0,id);
    }


    public boolean passwordMatch(SignIn signIn) {
        log.info("SignIn :: = {} ",signIn);
        if(!passwordEncoder.matches(signIn.getPassword(),memberMapper.getPassword(signIn.getEmail()))){
            return false;
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
