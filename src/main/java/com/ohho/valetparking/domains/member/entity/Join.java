package com.ohho.valetparking.domains.member.entity;

import lombok.Getter;
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
    private final String department;
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
        private final String department;
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
        if(this.department.equals("FS")){
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Join)) return false;
        Join join = (Join) o;
        return Objects.equals(nickname, join.nickname) && Objects.equals(email, join.email) && Objects.equals(password, join.password) && Objects.equals(department, join.department);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nickname, email, password, department);
    }
}
