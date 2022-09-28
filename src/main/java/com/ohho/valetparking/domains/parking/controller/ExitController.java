package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.domain.dto.ExitRequest;
import com.ohho.valetparking.domains.parking.domain.entity.ExitForRead;
import com.ohho.valetparking.domains.parking.service.ExitRequestService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    private final ExitRequestService exitRequestService;

    @GetMapping(value = "/exit-requests", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> getExitRequestList(){
        List<ExitForRead> exits = exitRequestService.getExitRequestList();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(exits));

    }

    @PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> requestExit( @RequestBody ExitRequest exitRequest ){
        log.info("[ExitController] ::: exitRequest = {}",exitRequest);
        String successMessage = exitRequestService.register(exitRequest.convertToExit());

        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success(successMessage));
    }

    @PostMapping(value = "/exit/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse> requestExit( @PathVariable("id") long exitRequestId, HttpServletRequest request){

        log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId );
        exitRequestService.approve( exitRequestId, request.getHeader("ACCESSTOKEN" ) );

        return ResponseEntity.status(HttpStatus.OK)
                             .body(SuccessResponse.success("출차 요청이 승인 되었습니다."));

    }

}
