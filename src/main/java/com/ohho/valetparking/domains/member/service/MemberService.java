package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.dto.JoinRequest;
import com.ohho.valetparking.domains.member.entity.User;
import com.ohho.valetparking.domains.member.entity.*;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        if(memberMapper.mailCheck(email)) {
            throw new SignUpFailException("이메일 중복");
        }
        // 메일 중복이 있을 경우, 커스텀 익셉션 만들어서 날리기
    }

    public void signIn(SignIn signIn) {
        // 비밀번호 확인
        passwordMatch(signIn);
        // 회원의 email로 id를 조회하고 로그인 기록 테이블에 기록 남긴다.
        recordSignIn(signIn);
    }

    public List<User> getUserList() {
        return memberMapper.userList();
    }


    // -----------------------------------------  sub method  -------------------------------------------------
    @Transactional
    private void recordSignIn(SignIn signIn) {
        log.info("signIn ={}" , signIn);
        long memberId = 0 ;
        if(signIn.isAdmin()){
            memberId = memberMapper.getAdminId(signIn.getEmail())
                                                     .orElseThrow(() -> new SignInFailException("관리자가 아니거나 업는 메일입니다."));
        }else{
            memberId = memberMapper.getMemberId(signIn.getEmail())
                                                      .orElseThrow(() -> new SignInFailException("사용자가 아니거나 업는 메일입니다."));
        }

        LoginHistory loginHistory = new LoginHistory(memberId,signIn.getDepartment());

        memberMapper.recordSignInHistory(loginHistory);
    }

    // admin인지 확인해야함
    public void passwordMatch(SignIn signIn) {
        log.info("MemberService :: SignIn :: = {} ",signIn);

        String password= signIn.getPassword();
        // 유저/멤버에서 통합 조회 후 db에 패스워드가 있는지 가져온다.
        String passwordFormDb = memberMapper.getPassword(signIn.getEmail());
                //Optional.ofNullable()
                                        // .orElseThrow(() -> {return new SignInFailException("이메일을 다시 입력해주세요.");});

        if(!passwordEncoder.matches(password,passwordFormDb)){
            throw new SignInFailException("비밀번호를 다시 입력해주세요.");
        }
    }

    @Transactional
    private void addMember(Join join) {
        log.info("Join ::: join ={}", join);
        int result = 0;

        if ( join.isAdmin() ) {
            result = memberMapper.newAdminJoin(join);
        }else {
            result = memberMapper.newUserJoin(join);
        }

        if ( result != 1 ) {
            throw new SignUpFailException("회원가입에 실패했습니다.");
        }
    }

    public List<Vip> getVip(String vipName) {
        return memberMapper.getVip(vipName);
    }
}


