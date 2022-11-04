package com.ohho.valetparking.domains.member.domain.entity;

import com.ohho.valetparking.domains.member.domain.dto.VipRequest;
import com.ohho.valetparking.domains.member.enums.VipType;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Role : Responsibility : Cooperation with :
 **/
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Vip implements Serializable {

  private Long id;
  private String name;
  private String car_number;
  private VipType type;
  private String uniqueness;


  public static Vip from(Long id, VipRequest vipRequest) {
    return new Vip(id, vipRequest.getName(), vipRequest.getCar_number(), vipRequest.getType(),
        vipRequest.getUniqueness());
  }
}
