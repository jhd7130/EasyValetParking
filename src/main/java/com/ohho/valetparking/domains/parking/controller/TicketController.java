package com.ohho.valetparking.domains.parking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.domains.parking.domain.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@RestController
@Slf4j
public class TicketController {
    private final TicketService ticketService;
    private final JWTProvider jwtProvider;

    public TicketController(TicketService ticketService, JWTProvider jwtProvider) {
        this.ticketService = ticketService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(value = "/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<Ticket> ticketRegister(@RequestBody @Valid TicketReqeust ticketReqeust, HttpServletRequest request) throws JsonProcessingException {
        log.info("TicketController ticketReqeust ::::: = {}, Header ={} ",ticketReqeust, request.getHeader("ACCESSTOKEN"));
        Ticket ticketIncludedEmail = ticketReqeust.toTicket(
                                                         jwtProvider.getTokenIngredientFromToken(request.getHeader("ACCESSTOKEN"))
                                                                    .getEmail()
                                                            );

        ticketService.register(ticketIncludedEmail);

        return SuccessResponse.success("성공");
    }

    /**
     * 아직 안됨
     */
    @GetMapping(value = "/ticket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ticketInformation(@PathVariable("id") final String id) {
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }


    @ApiIgnore
    @GetMapping(value = "/ticket/test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse<String> ticketInformationTest(@PathVariable("id") final String id) {
        TicketDuplicateException ticketDuplicateException = new TicketDuplicateException("티켓이 중복입니다.");

        return SuccessResponse.success(id);
    }


}
