package com.ohho.valetparking.domains.parking.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ohho.valetparking.domains.member.domain.dto.SignInResponse;
import com.ohho.valetparking.domains.member.domain.entity.Member;
import com.ohho.valetparking.domains.member.enums.MemberType;
import com.ohho.valetparking.domains.parking.domain.dto.TicketReqeust;
import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.domains.parking.service.TicketService;
import com.ohho.valetparking.global.common.dto.ApiResponse;
import com.ohho.valetparking.global.common.dto.SuccessResponse;
import com.ohho.valetparking.global.error.ErrorCode;
import com.ohho.valetparking.global.security.jwt.JWTProvider;
import com.ohho.valetparking.global.security.permission.PermissionRequired;
import com.ohho.valetparking.global.util.SessionUtil;
import io.swagger.v3.oas.annotations.Hidden;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    log.info("TicketController ticketReqeust ::::: = {}, Header ={} ", ticketReqeust,
        request.getHeader("ACCESSTOKEN"));
    Ticket ticketIncludedEmail = ticketReqeust.toTicket(
        jwtProvider.getTokenIngredientFromToken(request.getHeader("ACCESSTOKEN"))
            .getEmail()
    );

    ticketService.register(ticketIncludedEmail);

    return ApiResponse.success("성공");
  }

  /**
   * 아직 안됨
   */

  @GetMapping(value = "/ticket/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ApiResponse ticketInformation(@PathVariable("id") final String id) {
    return ApiResponse.success(id);
  }

  @Hidden
  @GetMapping(value = "/ticket/test/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SuccessResponse<String> ticketInformationTest(@PathVariable("id") final String id, HttpServletRequest request) {
//    TicketDuplicateException ticketDuplicateException = new TicketDuplicateException(
//        ErrorCode.DATA_DUPLICATE);
//    HttpSession httpSession = request.getSession();
//    String refreshtoken = SessionUtil.getRefreshtoken(request.getSession());
    return SuccessResponse.success(id);
  }


}
