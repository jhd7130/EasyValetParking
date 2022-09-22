package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.JoinRequest;
import com.ohho.valetparking.domains.member.domain.entity.*;
import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        log.info("[MemberService] ::: sigIn ={}",signIn);
        // 비밀번호 확인
        passwordMatch(signIn);
        recordSignInHistory(signIn);
    }

    public List<User> getUserList() {
        return memberMapper.userList();
    }


    // -----------------------------------------  sub method  -------------------------------------------------
    @Transactional
    private void recordSignInHistory(SignIn signIn) {
        log.info("signIn ={}" , signIn);
        int adminFlag = 0;
        long memberId = memberMapper.getAdminId(signIn.getEmail())
                               .orElseGet(() -> memberMapper.getMemberId(signIn.getEmail())
                                                            .orElseThrow(()-> new SignInFailException("이메일 이 존재하지 않습니다.")));

        // 그냥 관리자를 가져오자
        if(!memberMapper.isAdmin(signIn.getEmail())){
            adminFlag = 1;
        }

        LoginHistory loginHistory = LoginHistory.from( memberId, adminFlag ); // 관리자인지 아닌지만 체크 위에 로지을 변경

        memberMapper.recordSignInHistory(loginHistory);
    }

    // admin인지 확인해야함
    public void passwordMatch(SignIn signIn) {
        log.info("MemberService :: SignIn :: = {} ",signIn);

        String password= signIn.getPassword();
        // 유저/멤버에서 통합 조회 후 db에 패스워드가 있는지 가져온다.
        String passwordFormDb = memberMapper.getPassword(signIn.getEmail())
                                            .orElseThrow( () -> new IllegalArgumentException("없는 회원입니다."));

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


