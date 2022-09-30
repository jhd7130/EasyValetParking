package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.parking.domain.entity.Exit;
import com.ohho.valetparking.domains.parking.domain.entity.ExitForRead;
import com.ohho.valetparking.domains.parking.domain.entity.ExitRequestStatusChange;
import com.ohho.valetparking.domains.parking.exception.FailChangeExitRequestStatusException;
import com.ohho.valetparking.domains.parking.exception.FailExitRegistrationException;
import com.ohho.valetparking.domains.parking.repository.ExitRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Slf4j
public class ExitRequestService {
    private final ExitRequestMapper exitRequestMapper;
    private final ExitApproveService exitApproveService;

    public ExitRequestService(ExitRequestMapper exitRequestMapper, ExitApproveService exitApproveService) {
        assert exitRequestMapper != null; assert exitApproveService != null;
        this.exitRequestMapper = exitRequestMapper;
        this.exitApproveService = exitApproveService;
    }
    // 트랜젝션을 제거합니다. 트랜젝션을 제거하는 이유는 비용 부분에서 비효율적이기 때문에 제거합니다.
    // 현재 메서드의 작업은 하나입니다. 작업들이 아니기 때문에 문제가 발생한다고 해서 돌려야하는 작업의 묶음이 필요가 없습니다.
    //  @Transactional
    public String register(Exit exit){
        int result = exitRequestMapper.registerExitRequest(exit);
        if(result != 1) {
            throw new FailExitRegistrationException("출차요청 실패");
        }
        return getResultMessage(exit);
    }

    public List<ExitForRead> getExitRequestList() {
        return exitRequestMapper.getExitRequestList();
    }

    @Transactional
    public void approve( long exitRequestId, String token ) {

        // 1. 해당 요청 아이디를 가진 exit-request 테이블의 row에서 status를 승인으로 변경한다.
        ExitRequestStatusChange approveStatus = makeExitRequestStatus(exitRequestId,1);

        // 2. 예외 상황을 찾는다.
        validUpdateSuccess(exitRequestMapper.updateStatus(approveStatus));

        exitApproveService.approve(exitRequestId, token);

    }

    public void reject(long exitRequestId) {
        // 출차 완료 상태를 반려할 수 있을까?
        ExitRequestStatusChange rejectStatus = makeExitRequestStatus(exitRequestId,3);
        log.info("[ExitRequestService]::: reject() rejectStatus={}",rejectStatus);

        validUpdateSuccess(exitRequestMapper.updateStatus(rejectStatus));

    }


    private String getResultMessage(Exit exit){
        if(exit.isOuting()){
            return "외출 요청 성공";
        }
        return "출차 요청 성공";
    }

    private ExitRequestStatusChange makeExitRequestStatus(long exitRequestId, int status){
        return new ExitRequestStatusChange(exitRequestId,status);
    }

    // 쓰레드의 모든 스택 중에 3번째꺼만 가져옵니다. 스택의 양이 많아서 문제가 생기지 않을까?
    private void validUpdateSuccess( int updateCount ) {
        String invocationFunctionName = Thread.currentThread().getStackTrace()[2].getMethodName();
        if ( updateCount != 1 ) {

            throw new FailChangeExitRequestStatusException( invocationFunctionName + "에 실패 했습니다. ");

        }
    }
}
