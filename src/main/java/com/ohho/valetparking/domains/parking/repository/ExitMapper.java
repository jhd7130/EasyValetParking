package com.ohho.valetparking.domains.parking.repository;

import com.ohho.valetparking.domains.parking.domain.entity.Exit;
import com.ohho.valetparking.domains.parking.domain.entity.ExitForRead;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Repository
@Mapper
public interface ExitMapper {

    Integer registerExit(Exit exit);

    List<ExitForRead> getExitRequestList();
}
