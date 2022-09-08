package com.ohho.valetparking.domains.member.domain;

import com.ohho.valetparking.domains.member.dto.JoinRequest;
import lombok.ToString;

import java.util.Objects;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@ToString
public class Join {
    private final  String nickname;
    private final String email;
    private final String password;
    private final int department;
   // private  String company;

    private Join(JoinBuilder joinBuilder){
        this.nickname = joinBuilder.nickname;
        this.email = joinBuilder.email;
        this.password = joinBuilder.password;
        this.department = joinBuilder.department;
    }
    public static JoinBuilder builder(JoinRequest joinRequest){
        return new JoinBuilder(joinRequest);
    }

    public static class JoinBuilder {
        private final String nickname;
        private final String email;
        private String password;
        private final int department;
       // private  String company;
        public JoinBuilder(JoinRequest joinRequest) {
            this.nickname = joinRequest.getNickname();
            this.email = joinRequest.getEmail();
            this.password = joinRequest.getPassword();
            this.department = joinRequest.getDepartment();
        }

        public JoinBuilder password(String password){
            this.password = password;
            return this;
        }
        // company 추가할 때 사용
        public Join build(){
            return new Join(this);
        }
    }

    public boolean isAdmin(){
        return this.department == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Join)) return false;
        Join join = (Join) o;
        return department == join.department && Objects.equals(nickname, join.nickname) && Objects.equals(email, join.email) && Objects.equals(password, join.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, email, password, department);
    }
}
