package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.*;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;


@Service
@Slf4j
public class ConnectionService {

    @Autowired
    protected UserAccountRepository userAccountRepository;

    public void createAnAccount(CreateAccount createAccount) {
        if (createAccount.getPassword().equals(createAccount.getConfirmPassword())) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            userAccountRepository.save(new UserAccount(
                    createAccount.getEmail(),
                    createAccount.getName(), 0.0,
                    "USER",
                    encoder.encode(createAccount.getPassword()
                    )));
        } else {
            log.error("The two entries for the password don't match each other");
        }
    }

    public UserAccount getAccountInfo() {
        return userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get();
    }
}