package com.ohho.valetparking.domains.parking.controller;

import com.ohho.valetparking.domains.parking.entity.TicketReqeust;
import com.ohho.valetparking.domains.parking.service.TicketService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("/ticket")
    public ResponseEntity ticketRegister(@RequestBody TicketReqeust ticketReqeust, HttpServletRequest request){
        log.info("TicketController ticketReqeust ::::: = {} ",ticketReqeust);
        return ResponseEntity.status(HttpStatus.OK).body(ticketReqeust);
    }

    @GetMapping("/ticket/{id}")
    public ResponseEntity ticketInformation(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
