package com.ohho.valetparking.parking;

import com.ohho.valetparking.domains.parking.entity.TicketReqeust;
import com.ohho.valetparking.domains.parking.service.TicketService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

public class TicketServiceTest {
    @Mock
    public TicketService ticketService;

    @Test
    void 티켓_등록_테스트(){
        TicketReqeust ticketReqeust = new TicketReqeust(1234,"4351","1");


        assertThat(ticketService.register(ticketReqeust)).isEqualTo(1);
    }
}
