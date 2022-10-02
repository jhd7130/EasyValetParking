package com.ohho.valetparking.domains.parking.domain.entity;

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
     private final long parkingId;
     private final int status;


     public boolean isOuting(){
          if( type == 1) return  true;
          return false;
     }

}
