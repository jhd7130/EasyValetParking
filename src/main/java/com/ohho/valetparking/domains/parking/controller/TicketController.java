package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.entity.Ticket;
import com.ohho.valetparking.domains.parking.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.security.JWTProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@RestController
@Slf4j
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping(value = "/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ticketRegister(@RequestBody @Valid TicketReqeust ticketReqeust, HttpServletRequest request){
        log.info("TicketController ticketReqeust ::::: = {} ",ticketReqeust);
        Ticket ticketIncludedEmail = ticketReqeust.toTicket(
                                        JWTProvider.getEmailInFromToken(
                                                            request.getHeader("ACCESSTOKEN")
                                                            )
                                        );

        ticketService.register(ticketIncludedEmail);

        return ResponseEntity.status(HttpStatus.OK)
                             .body("티켓등록 성공");
    }

    @GetMapping(value = "/ticket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ticketInformation(@PathVariable("id") final String id) {
        return ResponseEntity.status(HttpStatus.OK)
                             .body(id);
    }
}
