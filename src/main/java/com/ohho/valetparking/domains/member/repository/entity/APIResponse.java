package com.ohho.valetparking.domains.member.repository.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@Setter
@ToString
public class APIResponse<T> implements Serializable {

    String status;
    String message;
    T data;

}
