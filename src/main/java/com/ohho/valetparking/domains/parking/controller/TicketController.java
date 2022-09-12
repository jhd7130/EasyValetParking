package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.entity.Ticket;
import com.ohho.valetparking.domains.parking.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.security.JWTProvider;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class TicketController {
    private final TicketService ticketService;

    @PostMapping(value = "/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Ticket> ticketRegister(@RequestBody @Valid TicketReqeust ticketReqeust, HttpServletRequest request){
        log.info("TicketController ticketReqeust ::::: = {} ",ticketReqeust);
        Ticket ticketIncludedEmail = ticketReqeust.toTicket(
                                        JWTProvider.getEmailInFromToken(
                                                            request.getHeader("ACCESSTOKEN")
                                                            )
                                        );

        ticketService.register(ticketIncludedEmail);

        return ApiResponse.success(ticketIncludedEmail);
    }

    @GetMapping(value = "/ticket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity ticketInformation(@PathVariable("id") final String id) {
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }





    @ApiIgnore
    @GetMapping(value = "/ticket/test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> ticketInformationTest(@PathVariable("id") final String id) {
        TicketDuplicateException ticketDuplicateException = new TicketDuplicateException("티켓이 중복입니다.");

        if(id.equals("1")) {
            return ApiResponse.fail("C200",ticketDuplicateException.getMessage(),HttpStatus.BAD_REQUEST);
        }

        return ApiResponse.success(id);
    }


}
