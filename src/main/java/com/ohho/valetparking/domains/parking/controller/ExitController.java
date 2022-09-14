package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.dto.ExitRequest;
import com.ohho.valetparking.domains.parking.service.ExitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

        List list =new ArrayList();
        list.add("ExitList : 리스트로 반환 될꺼야, 아직 완성은 안됐어~~!!");
        return ResponseEntity.status(HttpStatus.OK).body(list);

    }

    @PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity requestExit( @RequestBody ExitRequest exitRequest ){
        log.info("[ExitController] ::: exitRequest = {}",exitRequest);
        exitService.register(exitRequest.toExit());

        if(exitRequest.isOuting()) {
            return ResponseEntity.status(HttpStatus.OK).body(" 외출 요청 성공");
        }

        return ResponseEntity.status(HttpStatus.OK).body(" 출차 요청 성공");
    }

}
