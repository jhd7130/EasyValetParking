package com.ohho.valetparking.domains.parking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TicketReqeust {
    private  String ticket_number;
    private  String car_number;
    private  String parking_area;
}
