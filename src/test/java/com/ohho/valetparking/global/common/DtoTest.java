package com.ohho.valetparking.global.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.domains.parking.controller.TicketController;
import com.ohho.valetparking.domains.parking.domain.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

@WebMvcTest(controllers = TicketController.class
            , excludeAutoConfiguration = SecurityAutoConfiguration.class )
public class DtoTest {
    @MockBean
    private TicketService ticketService;
    @MockBean
    private JWTProvider jwtProvider;
    SuccessResponse apiResponse;
    ObjectMapper objectMapper;
    Ticket ticket;
    String jsontest;
    String test;
    @BeforeEach
    void setting() throws JsonProcessingException {
        // given
        apiResponse = SuccessResponse.success(ticket);
        objectMapper = new ObjectMapper();
        // when
        jsontest = objectMapper.writeValueAsString(apiResponse);
        test = jsontest.substring(jsontest.indexOf(','));
    }

    @Test
    void apiresponse_성공_테스트() {
        ticket = Ticket.builder(new TicketReqeust(245,"1234","test",""),"test@gamil.com").build();
        // then
        Assertions.assertThat(jsontest).isEqualTo("{\"localDateTime" + "\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+ "\"" + test);
    }

    @Test
    void apiresponse_실패_테스트() throws JsonProcessingException {
        // given
        ticket = Ticket.builder(new TicketReqeust(245,"1234","test","test"),"test@gamil.com").build();
        //then
        Assertions.assertThat(jsontest).isEqualTo("{\"localDateTime" + "\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+ "\"" + test);
    }
}

