package com.ohho.valetparking.domains.member.controller;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import com.ohho.valetparking.domains.member.service.VipService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Role : Responsibility : Cooperation with :
 **/
@RestController
public class VipApi {

  private final Logger log = LoggerFactory.getLogger(VipApi.class);
  private final VipService vipService;

  public VipApi(VipService vipService) {
    this.vipService = vipService;
  }

  //------------------------------------------------- CUD -----------------------------------------------
  @PostMapping(value = "/vip", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> registor(@RequestBody @Valid VipRequest vipRequest) {
    log.info("[VipApi] :::: vipRequest = {}", vipRequest);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(vipService.registerVip(vipRequest)));
  }

  @DeleteMapping(value = "/vip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> delete(@PathVariable @NonNull Long id) {
    log.info("[VipApi] :::: vipName = {}", id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(vipService.deleteVip(id)));
  }

  @PutMapping(value = "/vip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> update(@PathVariable @NonNull Long id,
      @RequestBody VipRequest vipRequest) {
    log.info("[VipApi] :::: vipName = {}", id);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(vipService.updateVip(Vip.from(id, vipRequest))));
  }

  //------------------------------------------------- R -------------------------------------------------

  @GetMapping(value = "/vip/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> getVipByName(@PathVariable("name") @NonNull String name) {
    log.info("[VipApi] :::: vipName = {}", name);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(vipService.findVipByName(name)));
  }

  @GetMapping(value = "/vip/car-number/{carNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> getVipByCarNumber(
      @PathVariable("carNumber") @NonNull String carNumber) {
    log.info("[VipApi] :::: vipName = {}", carNumber);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(vipService.findVipByCarNumber(carNumber)));
  }


  @GetMapping(value = "/vip/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> getVip(@PathVariable("id") @NonNull Long id) {
    log.info("[VipApi] :::: vipName = {}", id);

    return ResponseEntity.status(HttpStatus.OK)
        .body(SuccessResponse.success(vipService.findVipById(id)));
  }

  @GetMapping(value = "/vip", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<SuccessResponse> getVips() {
    log.info("[VipApi] :::: vipName = {}");

    return ResponseEntity.status(HttpStatus.OK).body(vipService.findVips());
  }
}
