package com.ohho.valetparking.domains.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@AllArgsConstructor
public class LoginHistory {
    private final long id;
    private final int isAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginHistory)) return false;
        LoginHistory that = (LoginHistory) o;
        return id == that.id && isAdmin == that.isAdmin;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isAdmin);
    }
}