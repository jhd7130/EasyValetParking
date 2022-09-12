package com.ohho.valetparking.domains.parking.entity;

import com.ohho.valetparking.domains.parking.dto.TicketReqeust;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
 **/
@Getter
@ToString
public class Ticket {
    private final String email;
    private final int ticketNumber;
    private final String carNumber;
    private final String parkingArea;
    private String customerName;

    private Ticket(TicketBuilder ticketBuilder) {
        this.email = ticketBuilder.email;
        this.ticketNumber = ticketBuilder.ticketNumber;
        this.carNumber = ticketBuilder.carNumber;
        this.parkingArea = ticketBuilder.parkingArea;
        this.customerName = ticketBuilder.customerName;
    }
    public static TicketBuilder builder(TicketReqeust ticketReqeust, String email){
        return new TicketBuilder(ticketReqeust, email);
    }

    public static class TicketBuilder{
        private final String email;
        private final int ticketNumber;
        private final String carNumber;
        private final String parkingArea;
        private String customerName;

        public TicketBuilder(TicketReqeust ticketReqeust,String email) {
            this.email = email;
            this.ticketNumber = ticketReqeust.getTicketNumber();
            this.carNumber = ticketReqeust.getCarNumber();
            this.parkingArea = ticketReqeust.getParkingArea();
            this.customerName = ticketReqeust.getCustomerName();
        }

        public Ticket build(){
            return new Ticket(this);
        }

    }

    public boolean isVIP() {
        return !(customerName == null || customerName.equals(""));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return ticketNumber == ticket.ticketNumber && Objects.equals(email, ticket.email) && Objects.equals(carNumber, ticket.carNumber) && Objects.equals(parkingArea, ticket.parkingArea) && Objects.equals(customerName, ticket.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, ticketNumber, carNumber, parkingArea, customerName);
    }
}
