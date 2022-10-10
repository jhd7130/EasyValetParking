package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import java.util.List;

/**
 * Role : Responsibility : Cooperation with :
 **/
public interface VipService {

  SuccessResponse<List<Vip>> findVipById(long id);

  SuccessResponse<List<Vip>> findVipByName(String VipName);

  SuccessResponse<List<Vip>> findVipByCarNumber(String CarNumber); //

  SuccessResponse<List<Vip>> findVips(); // 전체조회

  // ------------------------------ CUD ------------------------------
  SuccessResponse<String> registerVip(VipRequest vipRequest); // vip 등록

  SuccessResponse<String> deleteVip(Long id);

  SuccessResponse<String> updateVip(Vip vip);

}
