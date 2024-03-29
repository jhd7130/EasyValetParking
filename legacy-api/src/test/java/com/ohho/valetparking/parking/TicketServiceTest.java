package com.ohho.valetparking.parking;

import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.domain.dto.TicketReqeust;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
public class TicketServiceTest {

    @Autowired
    WebTestClient webTestClient;

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
