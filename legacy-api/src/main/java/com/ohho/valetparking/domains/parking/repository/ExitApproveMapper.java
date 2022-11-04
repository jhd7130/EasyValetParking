package com.ohho.valetparking.domains.parking.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Mapper
public interface ExitApproveMapper {
    int registerExitApprove(@Param("exitReqeustId") long exitReqeustId, @Param("approveAdminId") long approveAdminId);
}
