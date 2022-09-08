package com.ohho.valetparking.parking;

import com.ohho.valetparking.domains.parking.entity.Ticket;
import com.ohho.valetparking.domains.parking.dto.TicketReqeust;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

public class TicketServiceTest {

    @Test
    void 티켓요청_티켓_변환(){
    // given
        TicketReqeust ticketReqeust = new TicketReqeust(1234,"4351","B201",null);
        Ticket ticket = Ticket.builder(ticketReqeust,"test@gaver.com").build();
    // then
        assertThat(ticketReqeust.toTicket("test@gaver.com")).isEqualTo(ticket);
    }

    @Test
    void 티켓요청객체_만들기(){
    // given
        TicketReqeust ticketReqeust = new TicketReqeust(1234,"4351","B201",null);
        Ticket ticket = Ticket.builder(ticketReqeust,"test@gaver.com").build();
    // when
        boolean flag = ticket.isVIP();
    // then
        assertFalse(flag);
    }


}
