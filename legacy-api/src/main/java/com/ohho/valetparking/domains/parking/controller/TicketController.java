package com.ohho.valetparking.domains.parking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.domains.member.enums.MemberType;
import com.ohho.valetparking.domains.parking.domain.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.permission.PermissionRequired;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@Slf4j
public class TicketController {

  private final TicketService ticketService;
  private final JWTProvider jwtProvider;

  public TicketController(TicketService ticketService, JWTProvider jwtProvider) {
    this.ticketService = ticketService;
    this.jwtProvider = jwtProvider;
  }
  @PermissionRequired(permission = MemberType.ADMIN)
  @PostMapping(value = "/ticket", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse ticketRegister(@RequestBody @Valid TicketReqeust ticketReqeust,
      HttpServletRequest request) throws JsonProcessingException {

    log.info("TicketController ticketReqeust ::::: = {}, Header ={} ", ticketReqeust, request.getHeader("ACCESSTOKEN"));

    Ticket ticketIncludedEmail = ticketReqeust.toTicket(
        jwtProvider.getTokenIngredientFromToken(request.getHeader("ACCESSTOKEN"))
            .getEmail()
    );

    ticketService.register(ticketIncludedEmail);

    return ApiResponse.success("성공");
  }
  @GetMapping(value = "/ticket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse getTicketInformation(@PathVariable("id") @NonNull String id) {
    log.info("[TicketController] getTicketInformation :: = {}", id);

    return ApiResponse.success(id);
  }

}
