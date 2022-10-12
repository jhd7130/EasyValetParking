package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.parking.domain.entity.Parking;
import com.ohho.valetparking.domains.parking.domain.entity.ParkingCount;
import com.ohho.valetparking.domains.parking.domain.entity.ParkingStatusChanger;
import com.ohho.valetparking.domains.parking.exception.FailParkingRegistrationException;
import com.ohho.valetparking.domains.parking.exception.NotFoundParkingRecordException;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import com.ohho.valetparking.global.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Service
@Slf4j
public class ParkingService {
    private final ParkingMapper parkingMapper;
    public ParkingService(ParkingMapper parkingMapper) {
        assert parkingMapper != null;
        this.parkingMapper = parkingMapper;
    }

    public void register( HashMap<String,Long> parkingInfo ){
        int result = parkingMapper.register(parkingInfo);
        if(result != 1) {
            throw new FailParkingRegistrationException(ErrorCode.FAIL_REGISTER);
        }
    }
    @Transactional
    public void updateStatus(long parkingId,int status) {
        // 변경 사항은 외출 출차 뿐이다.(insert가 그냥 입차)
        ParkingStatusChanger parkingStatus = new ParkingStatusChanger(parkingId,status);
        parkingMapper.updateStatus( parkingStatus );
    }

    public List<ParkingCount> getParkingCount() {
        return parkingMapper.getParkingCount();
    }

    public List<Parking> getParkingRecordList() {

        // 만약 중간에 빈데이터가 있을 경우 필터링
        // Deprecate => stream.filter 제거 가능 이유는 아래와 같음
        // mybatis 설정에서 전체가 널인 row는 반환하지 않는 설정이 기본으로 되어 있다.
        // <setting name="returnInstanceForEmptyRow" value="true"/> 필요하다면 value를 false로 변경

        List<Parking> list = parkingMapper.getParkingRecordList()
                                          .stream()
                                          .filter(x -> x.getCarNumber() != null)
                                          .collect(Collectors.toList());

        if(list.size() == 0 || list == null) {
            throw new NotFoundParkingRecordException("주차 기록이 존재하지 않습니다.");
        }

        return list;
    }

    public Parking getParkingRecord(long id) {
        log.info("[ParkingService] ::::: id ={} " , id);
        Parking parking = parkingMapper.getParkingRecord(id)
                                       .orElseThrow(() -> new NotFoundParkingRecordException("해당 기록을 찾울 수 없습니다."));
        return parking;
    }

    private ParkingStatusChanger makeParkingStatus(long parkingId, int status){
            return new ParkingStatusChanger(parkingId,status);
    }


}
