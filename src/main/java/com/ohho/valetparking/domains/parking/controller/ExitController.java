package com.ohho.valetparking.domains.parking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
 * Role : Responsibility : Cooperation with :
 **/
@Slf4j
@RestController
@AllArgsConstructor
public class ExitController {

  private final ExitRequestService exitRequestService;

  // 권한 관리 : 사용자 관리자
  @GetMapping(value = "/exit-requests", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> getExitRequestList() {
    List<ExitForRead> exits = exitRequestService.getExitRequestList();
    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(exits));

  }

  // 권한 관리 : 사용자 관리자
  @PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> requestExit(@RequestBody ExitRequest exitRequest) {
    log.info("[ExitController] ::: exitRequest = {}", exitRequest);
    String successMessage = exitRequestService.register(exitRequest.convertToExit());

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(SuccessResponse.success(successMessage));
  }

  // 권한 관리 : 관리자
  @PostMapping(value = "/exit/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> requestExitApprove(@PathVariable("id") long exitRequestId,
      HttpServletRequest request) throws JsonProcessingException {

    log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId);
    exitRequestService.approve(exitRequestId, request.getHeader("ACCESSTOKEN"));

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success("출차 요청이 승인 되었습니다."));

  }

  // 권한 관리 : 관리자
  @PostMapping(value = "/exit/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> requestExitReject(@PathVariable("id") long exitRequestId) {

    log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId);
    exitRequestService.reject(exitRequestId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success("출차 요청이 반려 되었습니다."));

  }

  // 권한 관리 : 관리자
  @PostMapping(value = "/exit/{id}/done", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> requestExitDone(@PathVariable("id") long exitRequestId) {

    log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId);
    exitRequestService.done(exitRequestId);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success("출차가 완료 되었습니다."));

  }

}
