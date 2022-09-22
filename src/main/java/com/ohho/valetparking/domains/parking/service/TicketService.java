package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.member.repository.MemberMapper;
import com.ohho.valetparking.domains.member.repository.VipMapper;
import com.ohho.valetparking.domains.parking.domain.entity.Ticket;
import com.ohho.valetparking.domains.parking.exception.FailTicketRegistrationException;
import com.ohho.valetparking.domains.parking.exception.TicketDuplicateException;
import com.ohho.valetparking.domains.parking.repository.ParkingMapper;
import com.ohho.valetparking.domains.parking.repository.TicketMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

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
                                                    .orElseThrow(() -> new FailTicketRegistrationException("발렛직원이 아니면 티켓을 등록할 수 없습니다.")));
            parkingInfo.put("ticketId",ticketMapper.getTicketId( ticket.getTicketNumber() ));

            // VIP를 체크하는데 이름이 있는지 없는지만 체크한다. 이유는 VIP인 경우에만 이름을 입력한다.
            if(ticket.isVIP()) {
                parkingInfo.put("vipId",
                                vipMapper.getVipId(ticket.getCustomerName())
                                                         .orElseThrow(() -> new FailTicketRegistrationException("찾을 수 없는 VIP입니다."))
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
