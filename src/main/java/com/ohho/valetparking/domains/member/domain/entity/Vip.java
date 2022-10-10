package com.ohho.valetparking.domains.member.domain.entity;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.enums.VipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Getter
@ToString
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class Vip {

  private final Long id;
  private final String name;
  private final String car_number;
  private final VipType type;
  private final String uniqueness;


  public static Vip from(Long id, VipRequest vipRequest) {
    return new Vip(id, vipRequest.getName(), vipRequest.getCar_number(), vipRequest.getType(),
        vipRequest.getUniqueness());
  }
}
