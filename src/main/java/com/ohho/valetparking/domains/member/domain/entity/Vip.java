package com.ohho.valetparking.domains.member.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@ToString
@AllArgsConstructor
public class Vip {

      private final long id;
      private final String name;
      private final String car_number;
      private final String type;
      private final String uniqueness;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vip)) return false;
        Vip vip = (Vip) o;
        return id == vip.id && Objects.equals(name, vip.name) && Objects.equals(car_number, vip.car_number) && Objects.equals(type, vip.type) && Objects.equals(uniqueness, vip.uniqueness);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, car_number, type, uniqueness);
    }
}
