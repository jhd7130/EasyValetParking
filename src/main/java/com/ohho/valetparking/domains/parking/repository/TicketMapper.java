package com.ohho.valetparking.domains.parking.repository;

import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Repository
@Mapper
public interface TicketMapper {
    int ticketRegister(Ticket ticket);

    long getTicketId(int ticketNumber);

    boolean doubleCheck(int ticketNumber);
}
