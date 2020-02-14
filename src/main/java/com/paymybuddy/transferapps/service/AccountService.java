package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.dao.AccountDAO;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
public class AccountService {

    @Autowired
    private AccountDAO accountDAO = new AccountDAO();

    private static Scanner scan = new Scanner(System.in, "UTF-8");
    private static final Logger logger = LogManager.getLogger("AccountService");
    private UserAccount userAccount;

    public Boolean getConnection() {
        String email;
        String password;
        System.out.println("Please enter your email");
        email = scan.nextLine();
        System.out.println("Please enter your password");
        password = scan.nextLine();
        if (accountDAO.userConnected(email, password)) {
            userAccount = accountDAO.getUserInfo(email);
            System.out.println("Hello "+userAccount.getName()+"! Your are connected");
            return true;
        }
        return false;
    }

    public Boolean addABuddy() {
        String email;
        System.out.println("Please enter the email of your buddy, we hope he is registered with us!");
        email = scan.nextLine();
        if (accountDAO.addRelativeConnection(userAccount.getEmail(), email)) {
            System.out.println("Your are connected with" + email);
            return true;
        }
        return false;
    }


    public void getAccountInfo() {
        System.out.println("Info on "+ userAccount.getName()+":");
        System.out.println("Email: "+ userAccount.getEmail());
        System.out.println("Money credited on the account: "+ userAccount.getMoneyAmount());
    }
}
