package com.ohho.valetparking.parking;

import com.ohho.valetparking.domains.parking.entity.Parking;
import com.ohho.valetparking.domains.parking.exception.ParkingRecordNotFoundException;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import com.ohho.valetparking.domains.parking.service.ParkingService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/

@ExtendWith(MockitoExtension.class)
public class ParkingServiceTest {
    @Mock
    ParkingService parkingService;

    @Test
    @DisplayName("주차 기록 전체 조회")
    void testGetParkingList(){
    // given
        List<Parking> parkingList = new ArrayList<>();
        parkingList.add(
                new Parking(123,"권재현",2345,"2341"
                            ,"M2", LocalDateTime.now(),LocalDateTime.now(),0));
    // when
        Mockito.when(parkingService.getParkingRecordList()).thenReturn(parkingList);
        int size = parkingService.getParkingRecordList().size();
    // then
        assertThat(1).isEqualTo(size);
    }
}
