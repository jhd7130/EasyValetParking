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

     public boolean canApprove(){
          // 상태가 승인인 경우에만 완료가능
          return status == 1;
     }

}
