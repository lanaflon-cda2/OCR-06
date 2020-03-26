package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MainService {
    @Autowired
    private UserAccountRepository userAccountRepository;

    public UserAccount getUserAccountSession() {
        return userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get();
    }
}
