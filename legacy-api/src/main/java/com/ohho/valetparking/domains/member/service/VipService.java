package com.ohho.valetparking.domains.member.service;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import java.util.List;

/**
 * Role : Responsibility : Cooperation with :
 **/
public interface VipService {

  List<Vip> findVipById(long id);

  List<Vip> findVipByName(String VipName);

  List<Vip> findVipByCarNumber(String CarNumber); //

  List<Vip> findVips(); // 전체조회

  // ------------------------------ CUD ------------------------------
  String registerVip(VipRequest vipRequest); // vip 등록

  String deleteVip(Long id);

  String updateVip(Vip vip);

}
