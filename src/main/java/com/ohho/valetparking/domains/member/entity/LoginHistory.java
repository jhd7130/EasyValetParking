package com.ohho.valetparking.domains.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginHistory {
    private final long id;
    private final int isAdmin;
    public static LoginHistory from(long id, int isAdmin){
        return new LoginHistory(id,isAdmin);
    }
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