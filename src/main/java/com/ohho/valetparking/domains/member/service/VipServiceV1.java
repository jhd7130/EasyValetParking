package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import com.ohho.valetparking.domains.member.exception.FailVipRegisterException;
import com.ohho.valetparking.domains.member.repository.VipMapper;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.error.ErrorCode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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

  @Cacheable(key = "#id", value = "Vip")
  @Override
  public List<Vip> findVipById(long id) {

    List<Vip> vips = vipMapper.findVipById(id);
    log.info("VIPS {}", vips);

    return vips;

  }

  @Override
  public List<Vip> findVipByName(String vipName) {

    List<Vip> vips = vipMapper.findVipByName(vipName);
    log.info("VIPS {}", vips);

    return vips;
  }

  @Override
  public List<Vip> findVipByCarNumber(String carNumber) {

    List<Vip> vips = vipMapper.findVipByCarNumber(carNumber);
    log.info("VIPS {}", vips);

    return vips;
  }
  @Cacheable(key = "'vips'",value = "Vip")
  @Override
  public List<Vip> findVips() {

    List<Vip> vips = vipMapper.findAll();
    log.info("VIPS {}", vips);

    return vips;
  }

  @Override
  public String registerVip(VipRequest vipRequest) {

    log.info("[VipServiceV1] registerVip ={}", vipRequest);
    validUpdateSuccess(vipMapper.registerVip(vipRequest));

    return "등록에 성공했습니다.";
  }

  @Override
  public String deleteVip(Long vipId) {
    log.info("[VipServiceV1] registerVip ={}", vipId);
    validUpdateSuccess(vipMapper.deleteVip(vipId));
    return "삭제에 성공했습니다.";
  }

  @Override
  public String updateVip(Vip vip) {
    validUpdateSuccess(vipMapper.updateVip(vip));
    return "수정에 성공했습니다.";
  }

  private void validUpdateSuccess(int updateCount) {
    String invocationFunctionName = Thread.currentThread().getStackTrace()[2].getMethodName();
    if (updateCount != 1) {
      log.error("[VipServiceV1] registerVip {}에 실패했습니다.", invocationFunctionName);
      if (invocationFunctionName.equals("registerVip")) {
        throw new FailVipRegisterException(ErrorCode.FAIL_REGISTER_VIP);
      }

      if (!invocationFunctionName.equals("registerVip")) {
        throw new FailVipRegisterException(ErrorCode.FAIL_UPDATE_VIP);
      }
    }
  }
}
