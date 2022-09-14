package com.ohho.valetparking.parking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.domains.parking.controller.TicketController;
import com.ohho.valetparking.domains.parking.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.service.TicketService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@WebMvcTest(controllers = TicketController.class
            , excludeAutoConfiguration = SecurityAutoConfiguration.class ) // spring security에서 제외 시키기 위한 작업
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;


    @Test
    void apiresponseTest() throws Exception{
        // given
        final TicketReqeust ticketReqeust = new TicketReqeust(1234,"","B201","테스트");
        // when
          mockMvc.perform(MockMvcRequestBuilders.get("/ticket/test/3")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(content().string("{\"localDateTime" + "\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\"" + ",\"success\":true,\"data\":\"3\",\"errorResponse\":null}"))
                        .andDo(print());

    }
    @Test
    void 파킹_티켓_추가_실패_statusTest() throws Exception{
    // given
    final TicketReqeust ticketReqeust = new TicketReqeust(1234,"","B201","테스트");
    // when
    final ResultActions resultActions =
            mockMvc.perform(MockMvcRequestBuilders.post("/ticket")
                                                .content(objectMapper.writeValueAsString(ticketReqeust))
                                                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());

    }

    @Test
    void 파킹_티켓_추가_실패_exceptionTest() throws Exception{
        // given
        final TicketReqeust ticketReqeust = new TicketReqeust(1234,"","B201","테스트");
        // when
        final MvcResult mvcResult =
                mockMvc.perform(MockMvcRequestBuilders.post("/ticket")
                                                      .content(objectMapper.writeValueAsString(ticketReqeust))
                                                      .contentType(MediaType.APPLICATION_JSON))
                        .andExpect( e -> Assertions.assertTrue(e.getResolvedException()
                                                                .getClass()
                                                                .isAssignableFrom(MethodArgumentNotValidException.class))
                        )
                        .andReturn();
    }
}
