package com.ohho.valetparking.domains.member.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Repository
@Mapper
public interface VipMapper {
    Optional<Long> getVipId(String customerName);
}
