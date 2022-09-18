package com.ohho.valetparking.domains.parking.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ParkingCount {
    private final String parkingArea;
    private final int carCount;
}
