package com.ohho.valetparking.domains.parking.domain.enums;

import com.ohho.valetparking.domains.parking.exception.NotFoundStatusException;

import java.util.Arrays;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public enum ExitRequestStatus {
    // 출차 요청 관련 : 승인 대기, 승인, 출차 완료, 반려
    // 출차 요청 상태
    WAIT(0)
    ,APPROVE(1)
    ,DONE(2)
    ,REJECT(3)
    ;

    private int label;
    private ExitRequestStatus(int label){
        this.label = label;
    }

    public int getLabel() {
        return label;
    }

    // mybatis에서 converting할 때 Int형으로 status type 찾을 때 사용.
    // 전체를 돌아야해서 비용이 발생하지만 static을 선언하기엔 이 도메인에서 밖에 사용하지 않음.
    public static ExitRequestStatus findByLabel(int label){
        return Arrays.stream(values())
                     .filter(x -> x.label == label)
                     .findAny()
                     .orElseThrow(()-> new NotFoundStatusException());
    }
}
