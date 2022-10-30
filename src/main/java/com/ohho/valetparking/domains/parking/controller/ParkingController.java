package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.domain.entity.Parking;
import com.ohho.valetparking.domains.parking.domain.entity.ParkingCount;
import com.ohho.valetparking.domains.parking.service.ParkingService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ParkingController {

    private final ParkingService parkingService;

    @GetMapping(value = "/parkings", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getParkingList(){
        List<Parking> parkings = parkingService.getParkingRecordList();

        return ApiResponse.success(parkings);
    }

    @GetMapping(value = "/parking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getParking(@PathVariable long id){

        Parking parking = parkingService.getParkingRecord(id);

        return ApiResponse.success(parking);
    }
    @PatchMapping(value = "/parking/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse comback(@PathVariable long id){

        Parking parking = parkingService.comback(id);

        return ApiResponse.success(parking);
    }

    @GetMapping(value = "/parking-count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse getParkingCount(){
        List<ParkingCount> parkingCount = parkingService.getParkingCount();
        return ApiResponse.success(parkingCount);
    }
}
