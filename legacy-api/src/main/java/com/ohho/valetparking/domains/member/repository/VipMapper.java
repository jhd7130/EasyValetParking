package com.ohho.valetparking.domains.member.repository;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.domain.entity.Vip;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Repository
@Mapper
public interface VipMapper {

  Optional<Long> getVipId(String customerName);

  List<Vip> findAll();

  List<Vip> findVipByName(String name);

  List<Vip> findVipById(long vipId);

  List<Vip> findVipByCarNumber(String vipCarNumber);

  int registerVip(VipRequest vipRequest);

  int deleteVip(Long vipId);

  int updateVip(Vip vip);
}
