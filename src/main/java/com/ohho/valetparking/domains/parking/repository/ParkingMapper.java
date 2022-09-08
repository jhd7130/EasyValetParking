package com.ohho.valetparking.domains.parking.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;


@Repository
@Mapper
public interface ParkingMapper {

    int parkingRegister(HashMap<String,Long> parkingInfo);


}
