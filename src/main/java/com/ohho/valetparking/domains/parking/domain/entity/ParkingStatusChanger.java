package com.ohho.valetparking.domains.parking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@ToString
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class ParkingStatusChanger {

    private final long parkingId;
    private final int status;

}
