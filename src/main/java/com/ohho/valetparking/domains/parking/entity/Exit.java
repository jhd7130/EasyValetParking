package com.ohho.valetparking.domains.parking.entity;

import com.ohho.valetparking.domains.parking.dto.ExitRequest;
import lombok.*;

/**
 * 빌더 패턴을 사용하는 것은 필수여야 하는 필드들이 4개이상 넘어가고 추가적으로 필수적이지 않은 값들이 존재할때
 * 사용하는 것이 좋다. 이렇게 필드값의 개수가 적으면 굳이 빌더 패턴을 사용하지 않는 것이 좋다.
 * 즉 무조건 적인 빌더패턴의 남발은 좋지 않다.
 */
@Getter
@ToString
@EqualsAndHashCode
public class Exit {
    private final long memberId;
    private final long parkingRecordId;
    private final int exitType;

    private Exit(long memberId, long parkingRecordId, int exitType) {
        this.memberId = memberId;
        this.parkingRecordId = parkingRecordId;
        this.exitType = exitType;
    }

    public static Exit from(long memberId, long parkingRecordId, int exitType){
        return new Exit(memberId, parkingRecordId, exitType);
    }
    public static Exit from(ExitRequest exitRequest){
        return new Exit(exitRequest.getMemberId(), exitRequest.getParkingRecordId(), exitRequest.getExitType());
    }
}
