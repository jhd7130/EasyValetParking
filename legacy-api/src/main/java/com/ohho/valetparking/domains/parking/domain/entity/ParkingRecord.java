package com.ohho.valetparking.domains.parking.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;

@ToString
@AllArgsConstructor
@Slf4j
@EqualsAndHashCode
@Getter
public class ParkingRecord {

        private final long member_id;
        private final long ticket_id;
        private final long vip_id;
        private final Timestamp entrance_at;
        private final Timestamp exit_at;
        private final int status;

}
