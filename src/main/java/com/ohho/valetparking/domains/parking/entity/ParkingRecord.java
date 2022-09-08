package com.ohho.valetparking.domains.parking.entity;

import lombok.AllArgsConstructor;

import java.sql.Timestamp;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@AllArgsConstructor
public class ParkingRecord {

        private final long member_id;
        private final long ticket_id;
        private final long vip_id;
        private final Timestamp entrance_at;
        private final Timestamp exit_at;
        private final int status;

}
