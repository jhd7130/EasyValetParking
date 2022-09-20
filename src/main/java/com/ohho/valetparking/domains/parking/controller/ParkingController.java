package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.entity.Parking;
import com.ohho.valetparking.domains.parking.entity.ParkingCount;
import com.ohho.valetparking.domains.parking.service.ParkingService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(value = "/parkings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> getParkingList(){
        List<Parking> parkings = parkingService.getParkingRecordList();

        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(parkings));
    }

    @GetMapping(value = "/parking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> getParking(@PathVariable long id){

        Parking parking = parkingService.getParkingRecord(id);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(parking));
    }

    @GetMapping(value = "/parking-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> getParkingCount(){
        List<ParkingCount> parkingCount = parkingService.getParkingCount();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(parkingCount));
    }
}
