package com.ohho.valetparking.global.security.jwt;

import lombok.*;

/**
 *  이메일이 유니크 인덱스 처럼 사용되기 때문에 사용자 식별이 가능합니다.
 *
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TokenIngredient {


  private String email;
  private int department;

}
