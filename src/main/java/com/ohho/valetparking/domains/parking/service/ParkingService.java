package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.parking.entity.Parking;
import com.ohho.valetparking.domains.parking.entity.ParkingCount;
import com.ohho.valetparking.domains.parking.exception.ParkingRecordNotFoundException;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public List<ParkingCount> getParkingCount() {
        return parkingMapper.getParkingCount();
    }

    public List<Parking> getParkingRecordList() {

        // 만약 중간에 빈데이터가 있을 경우 필터링
        List<Parking> list = parkingMapper.getParkingRecordList()
                                          .stream()
                                          .filter(x -> x.getCarNumber() != null)
                                          .collect(Collectors.toList());

        if(list.size() == 0 || list == null) {
            throw new ParkingRecordNotFoundException("주차 기록이 존재하지 않습니다.");
        }

        return list;
    }
}
