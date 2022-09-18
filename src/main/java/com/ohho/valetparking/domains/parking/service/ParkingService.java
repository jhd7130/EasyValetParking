package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.parking.entity.ParkingCount;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Service
@AllArgsConstructor
@Slf4j
public class ParkingService {
    private final ParkingMapper parkingMapper;
    public List<ParkingCount> getParkingCount() {
        return parkingMapper.getParkingCount();
    }
}
