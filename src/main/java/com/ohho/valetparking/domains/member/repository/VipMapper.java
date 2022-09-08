package com.ohho.valetparking.domains.member.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Repository
@Mapper
public interface VipMapper {
    long getVipId(String customerName);
}
