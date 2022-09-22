package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.domain.dto.ExitRequest;
import com.ohho.valetparking.domains.parking.domain.entity.ExitForRead;
import com.ohho.valetparking.domains.parking.service.ExitService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Slf4j
@RestController
@AllArgsConstructor
public class ExitController {

    private final ExitService exitService;

    @GetMapping(value = "/exit-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getExitRequestList(){
        List<ExitForRead> exits = exitService.getExitRequestList();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(exits));

    }

    @PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity requestExit( @RequestBody ExitRequest exitRequest ){
        log.info("[ExitController] ::: exitRequest = {}",exitRequest);
        String successMessage = exitService.register(exitRequest.convertToExit());

        return ResponseEntity.status(HttpStatus.OK)
                             .body(successMessage);
    }

}
