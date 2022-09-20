package com.ohho.valetparking.domains.parking.repository;

import com.ohho.valetparking.domains.parking.entity.Parking;
import com.ohho.valetparking.domains.parking.entity.ParkingCount;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;


@Repository
@Mapper
public interface ParkingMapper {

    int parkingRegister(HashMap<String,Long> parkingInfo);
    List<ParkingCount> getParkingCount();
    List<Parking> getParkingRecordList();
}
