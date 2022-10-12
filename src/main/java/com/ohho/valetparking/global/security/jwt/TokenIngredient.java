package com.ohho.valetparking.global.security.jwt;

import lombok.*;

/**
 * Role : Responsibility : Cooperation with :
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
