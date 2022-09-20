package com.ohho.valetparking.global.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.domains.member.exception.SignUpFailException;
import com.ohho.valetparking.domains.parking.controller.TicketController;
import com.ohho.valetparking.domains.parking.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.entity.Ticket;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.security.JWTProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

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
    @Test
    void apiresponse_성공_테스트() throws JsonProcessingException {
    // given
        Ticket ticket = Ticket.builder(new TicketReqeust(245,"1234","test",""),"test@gamil.com").build();
        SuccessResponse apiResponse = SuccessResponse.success(ticket);
        ObjectMapper objectMapper = new ObjectMapper();
    // when
        String jsontest = objectMapper.writeValueAsString(apiResponse);
        String test = jsontest.substring(jsontest.indexOf(','));

        // then
        Assertions.assertThat(jsontest).isEqualTo("{\"localDateTime" + "\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+ "\"" + test);
    }

    @Test
    void apiresponse_실패_테스트() throws JsonProcessingException {
    // given
        Ticket ticket = Ticket.builder(new TicketReqeust(245,"1234","test","test"),"test@gamil.com").build();
        SuccessResponse apiResponse = SuccessResponse.success(ticket);
        ObjectMapper objectMapper = new ObjectMapper();
    // when
          String jsontest = objectMapper.writeValueAsString(apiResponse);
          String test = jsontest.substring(jsontest.indexOf(','));
    // then
        System.out.println(jsontest);
        Assertions.assertThat(jsontest).isEqualTo("{\"localDateTime" + "\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))+ "\"" + test);
    }


}

