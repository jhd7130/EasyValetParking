package com.ohho.valetparking.domains.parking.dto;

import com.ohho.valetparking.domains.parking.entity.Ticket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TicketReqeust {

    @NotNull(message = "티켓 번호를 입력해주세요.")
    private  int ticketNumber;

    @NotBlank(message = "차량 번호를 입력해주세요.")
    private  String carNumber;

    @NotNull(message = "주차 구역을 입력해주세요.")
    private  String parkingArea;

    private String customerName;

    public Ticket toTicket(String email ){
        // 여기서 jwttoken을 매개변수로 받아 추출하면 테스트 하기가 힘들어 진다.
        return Ticket.builder(this,email).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TicketReqeust)) return false;
        TicketReqeust that = (TicketReqeust) o;
        return ticketNumber == that.ticketNumber && Objects.equals(carNumber, that.carNumber) && Objects.equals(parkingArea, that.parkingArea) && Objects.equals(customerName, that.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNumber, carNumber, parkingArea, customerName);
    }
}
