package com.ohho.valetparking.domains.parking.entity;

import lombok.*;


@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class ExitForRead {

     private final long exitRequestId;
     private final String carNumber;
     private final int type;
     private final String parkingArea;

}
