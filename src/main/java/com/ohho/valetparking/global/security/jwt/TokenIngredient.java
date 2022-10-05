package com.ohho.valetparking.global.security.jwt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class TokenIngredient {
    private final String email;
    private final int department;
}
