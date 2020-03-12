package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.*;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.repositories.PasswordRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;


@Service
@Slf4j
public class ConnectionService extends MainService {


    @Autowired
    private PasswordRepository passwordRepository;

    public UserAccount getConnection(Logs logs) {
        String email = logs.getEmail();
        String password = logs.getPassword();
        if (userAccountRepository.findByEmail(email).isPresent()
                && passwordRepository.findFirstByEmail(email).getPassword().equals(password)) {
            userAccountSession = userAccountRepository.findByEmail(email).stream().findFirst().get();
            userAccountSession.setDatelog(Timestamp.from(Instant.now()));
            userAccountRepository.save(userAccountSession);
            return userAccountSession;
        } else {
            log.error("Wrong email or password");
            return null;
        }
    }

    public void disconnect() {
        userAccountSession.setDatelog(Timestamp.from(Instant.ofEpochMilli(0)));
        userAccountRepository.save(userAccountSession);
        userAccountSession = null;
    }

    public Boolean isConnected() {
        if (userAccountSession != null) {
            if (userAccountSession.getDatelog().after(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 4))) {
                userAccountSession.setDatelog(Timestamp.from(Instant.now()));
                userAccountRepository.save(userAccountSession);
                return true;
            } else {
                log.error("You have been disconnected because of you have been inactive too long");
                return false;
            }
        } else {
            log.error("You are not connected, please connect first or create an account");
            return false;
        }
    }


    public void createAnAccount(CreateAccount createAccount) {
        if (createAccount.getPassword().equals(createAccount.getConfirmPassword())) {
            userAccountRepository.save(new UserAccount(createAccount.getEmail(), createAccount.getName(), 0.0, Timestamp.from(Instant.ofEpochMilli(0))));
            passwordRepository.save(new Password(createAccount.getEmail(), createAccount.getPassword()));
        } else {
            log.error("The two entries for the password don't match each other");
        }
    }

    public UserAccount getAccountInfo() {
        return userAccountSession;
    }
}