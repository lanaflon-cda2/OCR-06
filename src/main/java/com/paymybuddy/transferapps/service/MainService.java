package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MainService {

    protected static UserAccount userAccountSession;

    @Autowired
    protected UserAccountRepository userAccountRepository;

    public void setUserAccountSession(UserAccount userAccount){
        userAccountSession=userAccount;
    }
    public UserAccount getUserAccountSession(){
        return userAccountSession;
    }
}
