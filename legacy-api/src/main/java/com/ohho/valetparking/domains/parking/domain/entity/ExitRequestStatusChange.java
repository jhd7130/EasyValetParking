package com.ohho.valetparking.domains.parking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@ToString
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class ExitRequestStatusChange {
    private final long exitRequestId;
    private final int status;
}
