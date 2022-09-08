package com.ohho.valetparking.domains.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private long id;
    private String nickname;
    private String email;
    private int department;
    private int activated;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id && department == user.department && activated == user.activated && Objects.equals(nickname, user.nickname) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nickname, email, department, activated);
    }
}
