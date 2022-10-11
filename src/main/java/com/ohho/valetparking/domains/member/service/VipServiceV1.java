package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import com.ohho.valetparking.domains.member.exception.FailVipRegisterException;
import com.ohho.valetparking.domains.member.repository.VipMapper;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Service
@Slf4j
public class VipServiceV1 implements VipService {

  private final VipMapper vipMapper;

  public VipServiceV1(VipMapper vipMapper) {
    this.vipMapper = vipMapper;
  }

  @Override
  public SuccessResponse<List<Vip>> findVipById(long id) {

    List<Vip> vips = vipMapper.findVipById(id);
    log.info("VIPS {}", vips);

    return SuccessResponse.success(vips);

  }

  @Override
  public SuccessResponse<List<Vip>> findVipByName(String vipName) {

    List<Vip> vips = vipMapper.findVipByName(vipName);
    log.info("VIPS {}", vips);

    return SuccessResponse.success(vips);
  }

  @Override
  public SuccessResponse<List<Vip>> findVipByCarNumber(String carNumber) {

    List<Vip> vips = vipMapper.findVipByCarNumber(carNumber);
    log.info("VIPS {}", vips);

    return SuccessResponse.success(vips);
  }

  @Override
  public SuccessResponse<List<Vip>> findVips() {
    List<Vip> vips = vipMapper.findAll();
    log.info("VIPS {}", vips);
    return SuccessResponse.success(vips);
  }

  @Override
  public SuccessResponse<String> registerVip(VipRequest vipRequest) {
    log.info("[VipServiceV1] registerVip ={}", vipRequest);
    validUpdateSuccess(vipMapper.registerVip(vipRequest));

    return SuccessResponse.success("등록에 성공했습니다.");
  }

  @Override
  public SuccessResponse<String> deleteVip(Long vipId) {
    log.info("[VipServiceV1] registerVip ={}", vipId);
    validUpdateSuccess(vipMapper.deleteVip(vipId));
    return SuccessResponse.success("삭제에 성공했습니다.");
  }

  @Override
  public SuccessResponse<String> updateVip(Vip vip) {
    validUpdateSuccess(vipMapper.updateVip(vip));
    return SuccessResponse.success("삭제에 성공했습니다.");
  }

  private void validUpdateSuccess(int updateCount) {
    String invocationFunctionName = Thread.currentThread().getStackTrace()[2].getMethodName();
    if (updateCount != 1) {
      log.error("[VipServiceV1] registerVip {}에 실패했습니다.", invocationFunctionName);
      throw new FailVipRegisterException(invocationFunctionName + "에 실패 했습니다. ");
    }
  }
}