package com.ohho.valetparking.domains.parking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.domains.member.enums.MemberType;
import com.ohho.valetparking.domains.parking.domain.dto.ExitRequest;
import com.ohho.valetparking.domains.parking.domain.entity.ExitForRead;
import com.ohho.valetparking.domains.parking.service.ExitRequestService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.security.permission.PermissionRequired;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
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
  @GetMapping(value = "/exits", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> getExitRequestList() {
    List<ExitForRead> exits = exitRequestService.getExitRequestList();
    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(exits));

  }

  // 권한 관리 : 사용자 관리자
  @PostMapping(value = "/exit", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse requestExit(@RequestBody @Valid ExitRequest exitRequest) {
    log.info("[ExitController] ::: exitRequest = {}", exitRequest);
    String successMessage = exitRequestService.register(exitRequest.convertToExit());

    return ApiResponse.success(successMessage);
  }

  // 권한 관리 : 관리자
  @PermissionRequired(permission = MemberType.ADMIN)
  @PostMapping(value = "/exit/{id}/approve", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse approveRequestExit(@PathVariable("id") @NonNull long exitRequestId,
      HttpServletRequest request) throws JsonProcessingException {

    log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId);
    exitRequestService.approve(exitRequestId, request.getHeader("ACCESSTOKEN"));

    return ApiResponse.success("출차 요청이 승인 되었습니다.");

  }

  // 권한 관리 : 관리자 사용자.
  @PatchMapping(value = "/exit/{id}/reject", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse rejectRequestExit(@PathVariable("id") @NonNull long exitRequestId) {

    log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId);
    exitRequestService.reject(exitRequestId);

    return ApiResponse.success("출차 요청이 반려 되었습니다.");

  }

  // 권한 관리 : 관리자
  @PermissionRequired(permission = MemberType.ADMIN)
  @PostMapping(value = "/exit/{id}/done", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse requestExitDone(@PathVariable("id") @NonNull long exitRequestId) {

    log.info("[ExitController] requestExit :::: exitRequestId ={}", exitRequestId);
    exitRequestService.done(exitRequestId);

    return ApiResponse.success("출차가 완료 되었습니다.");

  }

}
