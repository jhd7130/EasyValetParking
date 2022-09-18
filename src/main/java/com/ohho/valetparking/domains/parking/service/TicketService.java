package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.member.exception.SignInFailException;
import com.ohho.valetparking.domains.member.repository.MemberMapper;
import com.ohho.valetparking.domains.member.repository.VipMapper;
import com.ohho.valetparking.domains.parking.entity.Ticket;
import com.ohho.valetparking.domains.parking.exception.FailTicketRegistrationException;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import com.ohho.valetparking.domains.parking.repository.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class TicketService {

    private final TicketMapper ticketMapper;
    private final VipMapper vipMapper;
    private final ParkingMapper parkingMapper;
    private final MemberMapper memberMapper;

    // 반환 없이 globalException을 적용하는 것 자체가 너무 spring에 의조하는 것은 아닌지.
    // 냄새가 많이 난다. 리펙토링 필요. 2022.06.03
    @Transactional
    public void register(Ticket ticket) {
            log.info("[TicketService] register ::: ticket = {} ",ticket);
            HashMap<String,Long> parkingInfo = new HashMap();

            ticketDuplicateCheck(ticket.getTicketNumber());
            if( ticketMapper.ticketRegister(ticket) != 1){
                    throw new FailTicketRegistrationException();
            }


            parkingInfo.put("managerId",memberMapper.getAdminId(ticket.getEmail())
                                                    .orElseThrow(() -> new FailTicketRegistrationException("발렛직원이 아니거나 업는 직원입니다.")));
            parkingInfo.put("ticketId",ticketMapper.getTicketId( ticket.getTicketNumber() ));

            if(ticket.isVIP()) {
                parkingInfo.put("vipId",
                                vipMapper.getVipId(ticket.getCustomerName())
                                                         .orElseThrow(() -> new FailTicketRegistrationException("VIP 존재하지 않습니다."))
                    );
            };

            parkingMapper.parkingRegister(parkingInfo);
    }

    private void ticketDuplicateCheck(int ticketNumber){
        if(ticketMapper.doubleCheck(ticketNumber)){
            throw new TicketDuplicateException("티켓 번호가 이미 존재합니다.");
        }
    }
}
