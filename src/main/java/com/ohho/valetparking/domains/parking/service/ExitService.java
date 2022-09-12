package com.ohho.valetparking.domains.parking.service;

import com.ohho.valetparking.domains.parking.entity.Exit;
import com.ohho.valetparking.domains.parking.exception.FailExitRegistrationException;
import com.ohho.valetparking.domains.parking.repository.ExitMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Role :
 * Responsibility :
 * Cooperation with :
**/

@Service
@AllArgsConstructor
public class ExitService {

    private final ExitMapper exitMapper;

    public void register(Exit exit){
        Integer result = exitMapper.registerExit(exit);
        if(result != 1) {
            throw new FailExitRegistrationException();
        }
    }

}
