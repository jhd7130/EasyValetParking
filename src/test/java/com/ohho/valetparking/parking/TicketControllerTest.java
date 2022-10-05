package com.ohho.valetparking.parking;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ohho.valetparking.domains.parking.controller.TicketController;
import com.ohho.valetparking.domains.parking.domain.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.jwt.TokenIngredient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @WebMvcTest의 경우 자동으로 configuration하는 빈들은 생성한다. 이외에 다른 것들은 scan이 불가능하다.
 * WebMvcconfiguer을 구현시켜 만든 인터셉터를 위한 interceptorConfiguration이 존재합니다. 그렇게 되면 scan하여 빈을 생성합니다.
 * 하지만 인터셉터에 등록되어 사용되는 AuthLoginInterceptor의 경우 @Component가 등록되어 있기 때문에 WebMvcTest가 스캔하지 못합니다.
 **/
@WebMvcTest(controllers = {TicketController.class}
            , excludeAutoConfiguration = SecurityAutoConfiguration.class
            , excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = WebMvcConfigurer.class) }) // spring security에서 제외 시키기 위한 작업
public class TicketControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TicketService ticketService;
    @MockBean
    private JWTProvider jwtProvider;

    @BeforeEach
    void settingJwt(){
        JWTProvider testJwtProvider = new JWTProvider();
        ReflectionTestUtils.setField(testJwtProvider,"SECREAT_KEY","thisistestsecretkeyformakingtokenbuthavetolongerthen256bitscausethisalgorithmishs256");
        ReflectionTestUtils.setField(testJwtProvider,"REFRESHTOKENTIME","1");
        ReflectionTestUtils.setField(testJwtProvider,"ACCESSTOKENTIME","1");
        this.jwtProvider = testJwtProvider;
    }

    @Test
    void apiresponseTest() throws Exception{
        // given
        final TicketReqeust ticketReqeust = new TicketReqeust(1234,"","B201","테스트");
        String token = jwtProvider.accessTokenCreate(new TokenIngredient("test@maver.com",0));

        // when
          mockMvc.perform(MockMvcRequestBuilders.get("/ticket/test/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("ACCESSTOKEN", token))
                        .andExpect(content().string("{\"localDateTime" + "\":\"" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "\"" + ",\"success\":true,\"data\":\"3\"}"))
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
