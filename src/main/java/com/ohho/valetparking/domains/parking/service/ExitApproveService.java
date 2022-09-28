package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.member.service.MemberService;
import com.ohho.valetparking.domains.parking.repository.ExitApproveMapper;
import com.ohho.valetparking.domains.parking.repository.ExitRequestMapper;
import com.ohho.valetparking.global.security.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Service
@Slf4j
public class ExitApproveService {

    private final MemberService memberService;
    private final ExitApproveMapper exitApproveMapper;
    private final JWTProvider jwtProvider;

    public ExitApproveService( MemberService memberService, JWTProvider jwtProvider, ExitApproveMapper exitApproveMapper) {

        assert jwtProvider != null; assert memberService != null;
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
        this.exitApproveMapper = exitApproveMapper;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void approve( long exitRequestId, String accessToken ) {
        log.info("[ExitApproveService] approve() :: exitRequestId = {}",exitRequestId);
        // 0. jwtoken에서 Email cwk
        String email =jwtProvider.getEmailInFromToken(accessToken);

        // 1. id 찾아오기
        long approveAdminId = memberService.getAdminIdByMail(email);

        // 2. approve 테이블에 admin id 랑 exit_reqeust_id 를 추가
        exitApproveMapper.registerExitApprove(exitRequestId,approveAdminId);
    }

}