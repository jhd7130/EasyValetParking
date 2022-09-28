package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.member.service.MemberService;
import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.exception.FailTicketRegistrationException;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import com.ohho.valetparking.domains.parking.repository.TicketMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Slf4j
public class TicketService {
    private final TicketMapper ticketMapper;
    private final ParkingService parkingService;
    private final MemberService memberService;

    public TicketService(TicketMapper ticketMapper, ParkingService parkingService, MemberService memberService) {
        assert ticketMapper != null; assert parkingService != null; assert memberService != null;
        this.ticketMapper = ticketMapper;
        this.parkingService = parkingService;
        this.memberService = memberService;
    }
    @Transactional
    public void register(Ticket ticket) {
            log.info("[TicketService] register ::: ticket = {} ",ticket);
            ticketDuplicateCheck(ticket.getTicketNumber());

            if( ticketMapper.ticketRegister(ticket) != 1){
                    throw new FailTicketRegistrationException("티켓 등록에 실패 했습니다.");
            }

            parkingService.register(makeParkingInformation(ticket));
    }

    private void ticketDuplicateCheck(int ticketNumber){
        if(ticketMapper.doubleCheck(ticketNumber)){
            throw new TicketDuplicateException("티켓 번호가 이미 존재합니다.");
        }
    }

    private HashMap<String,Long> makeParkingInformation(Ticket ticket){

        HashMap<String,Long> parkingInfo = new HashMap();

        parkingInfo.put("managerId",memberService.getAdminIdByMail(ticket.getEmail()));
        parkingInfo.put("ticketId",ticketMapper.getTicketId( ticket.getTicketNumber() ));

        // VIP를 체크하는데 이름이 있는지 없는지만 체크한다. 이유는 VIP인 경우에만 이름을 입력한다.
        if(ticket.isVIP()) {
            parkingInfo.put("vipId",memberService.getVipId(ticket.getCustomerName()));
        }

        return parkingInfo;

    }
}
