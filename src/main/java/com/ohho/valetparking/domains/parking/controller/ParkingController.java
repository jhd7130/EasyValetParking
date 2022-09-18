package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.entity.ParkingCount;
import com.ohho.valetparking.domains.parking.service.ParkingService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@RestController
@Slf4j
@AllArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping("/parkings")
    public ResponseEntity getParkingList(){
        List list =new ArrayList();
        list.add("ParkingList : 리스트로 갈꺼야");
        //현재 주차된 차량 정보 모두 조회 후 반환
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/parking-count")
    public ResponseEntity<SuccessResponse> getParkingCount(){
        List<ParkingCount> parkingCount = parkingService.getParkingCount();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(parkingCount));
    }
}
