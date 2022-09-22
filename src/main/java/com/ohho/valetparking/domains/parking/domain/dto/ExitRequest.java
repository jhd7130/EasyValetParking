package com.ohho.valetparking.domains.parking.domain.dto;

import com.ohho.valetparking.domains.parking.domain.entity.Exit;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * @Value를 사용하는 것이 가독성이 좋지만 모두다 안다는 가정은 좋지 않다.
 * 모르는 사람들이 있을 수 있다는 생각으로 개발한다.
 **/

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ExitRequest {
    @NotBlank(message = "요청자 정보가 없습니다.")
    private long memberId;
    @NotBlank(message = "주차 정보가 없습니다.")
    private long parkingRecordId;
    @NotBlank(message = "출차 유형 정보가 없습니다.")
    private int exitType;

    public Exit convertToExit(){
        return Exit.from(this);
    }


}
