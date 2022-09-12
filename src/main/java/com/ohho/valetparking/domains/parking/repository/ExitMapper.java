package com.ohho.valetparking.domains.parking.repository;

import com.ohho.valetparking.domains.parking.entity.Exit;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Repository
@Mapper
public interface ExitMapper {

    Integer registerExit(Exit exit);

}
