package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.constants.StateEnum;
import com.paymybuddy.transferapps.dao.AccountDAO;
import com.paymybuddy.transferapps.domain.Transaction;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.util.InputReaderUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AccountService {

    private static final Logger logger = LogManager.getLogger("AccountService");
    private UserAccount userAccount;

    @Autowired
    private AccountDAO accountDAO;

    public UserAccount getConnection(String email, String password) {
        if (accountDAO.isGoodLogs(email, password)) {
            UserAccount userAccount = accountDAO.getUserInfo(email);
            this.userAccount = userAccount;
            return userAccount;
        }
        return null;
    }

    public void disconnect() {
        accountDAO.diconnect(userAccount.getEmail());
        this.userAccount = null;
    }

    public Boolean isConnected() {
        if (userAccount.getEmail().isEmpty()) {
            return false;
        } else {
            return accountDAO.isConnected(userAccount.getEmail());
        }
    }

    public Boolean addAFriend(String email) {
        if (accountDAO.addRelativeConnection(userAccount.getEmail(), email)) {
            System.out.println("Your are connected with" + email);
            return true;
        }
        return false;
    }

    public void addABankAccount(String bankName) {
        // TODO: create a service in order to entering the IBAN
        Integer character = (int) (Math.random() * 20);
        String iban = character.toString();
        accountDAO.addBankAccount(userAccount.getEmail(), bankName, iban);
    }

    public void withDrawMoneyFromBankAndAddOnTheAccount(String bankAccountName, Double amount) {
        accountDAO.tradingWithBank(userAccount.getEmail(), bankAccountName, amount);
        String description = "Withdraw my Money from a bank Account";
        accountDAO.addTransaction(userAccount.getEmail(), bankAccountName, amount, 0.0, description);
        userAccount = accountDAO.getUserInfo(userAccount.getEmail());
    }

    public void depositMoneyToBankAccount(String bankAccountName, Double amount) {
        accountDAO.tradingWithBank(userAccount.getEmail(), bankAccountName, -amount);
        String description = "Sending my money to a bank Account";
        accountDAO.addTransaction(userAccount.getEmail(), bankAccountName, -amount, 0.0, description);
        userAccount = accountDAO.getUserInfo(userAccount.getEmail());
    }

    public void sendMoneyToARelative(String relativeEmail, Double amount, String description) {
        Double taxApps;
        taxApps = 0.05 * amount;
        //debit the account of the sender
        accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
        //credit the account of the receiver
        accountDAO.updateMoneyOnAccount(relativeEmail, amount);
        //debit the account 5% of the amount of the transaction  and deposit on the account of the company PayMyBuddy
        accountDAO.tradingWithBank(userAccount.getEmail(), "BankOfPatMyBuddyCompany", taxApps);
        //recording the transaction
        accountDAO.addTransaction(userAccount.getEmail(), relativeEmail, amount, taxApps, description);
        userAccount = accountDAO.getUserInfo(userAccount.getEmail());
    }


    //Getters

    public List<Transaction> getTransactionInfo() {
        return accountDAO.getTransaction(userAccount.getEmail());
    }

    public UserAccount getAccountInfo() {
        return userAccount;
    }

    public List<String> getBankAccounts() {
        return accountDAO.getBankAccounts(userAccount.getEmail());
    }

    public List<String> getRelatives() {
        return accountDAO.getRelatives(userAccount.getEmail());
    }
}