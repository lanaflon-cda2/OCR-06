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

import java.nio.charset.Charset;
import java.util.List;


@Service
public class AccountServiceOffline {

    private static final Logger logger = LogManager.getLogger("AccountService");
    public UserAccount userAccount;

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private InputReaderUtil inputReaderUtil;


    public Boolean getConnection() {
        String email = inputReaderUtil.readString(StateEnum.EMAIL.getStr());
        String password = inputReaderUtil.readString(StateEnum.PASSWORD.getStr());
        if (accountDAO.userConnected(email, password)) {
            userAccount = accountDAO.getUserInfo(email);
            System.out.println("Hello " + userAccount.getName() + "! Your are connected");
            return true;
        }
        return false;
    }

    public Boolean addABuddy() {
        String email = inputReaderUtil.readString(StateEnum.CONNECTION_EMAIL.getStr());
        if (accountDAO.addRelativeConnection(userAccount.getEmail(), email)) {
            System.out.println("Your are connected with" + email);
            return true;
        }
        return false;
    }

    public void addABankAccount() {
        String bankName = inputReaderUtil.readString(StateEnum.NEW_BANKACCOUNT.getStr());
        // TODO: create a service in order to entering the IBAN
        String iban = new String(new byte[20], Charset.forName("UTF-8"));
        accountDAO.addBankAccount(userAccount.getEmail(), bankName, iban);
    }

    public void withDrawMoneyFromBankAndAddOnTheAccount() {
        Double amount = inputReaderUtil.readDouble(StateEnum.AMOUNT_WITHDRAW.getStr());
        accountDAO.updateMoneyOnAccount(userAccount.getEmail(), amount);
        userAccount = accountDAO.getUserInfo(userAccount.getEmail());
    }

    public void sendMoneyToARelative() {
        Double amount;
        Double taxApps;
        Double amountRemaining;
        String description;
        int friendID;
        List<String> friends = accountDAO.getRelatives(userAccount.getEmail());
        if (friends.isEmpty()) {
            logger.error(StateEnum.NO_RELATIVES.getStr());
        } else {
            for (int i = 0; i < friends.size(); i++) {
                System.out.println(i + ": " + friends.get(i));
            }
            friendID = inputReaderUtil.readInt(StateEnum.CHOOSE_FRIEND.getStr());
            amount = inputReaderUtil.readDouble(StateEnum.AMOUNT_SEND.getStr());
            description = inputReaderUtil.readString(StateEnum.DESCRIPTION.getStr());
            taxApps = 0.05 * amount;
            amountRemaining = 0.95 * amount;
            accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
            accountDAO.updateMoneyOnAccount(friends.get(friendID), amountRemaining);
            accountDAO.addTransaction(userAccount.getEmail(), friends.get(friendID), amount, taxApps, description);
            userAccount = accountDAO.getUserInfo(userAccount.getEmail());
        }
    }

    public void sendMoneyToBankAccount() {
        List<String> banks = accountDAO.getBankAccounts(userAccount.getEmail());
        if (banks.isEmpty()) {
            logger.error(StateEnum.NO_BANKACCOUNT.getStr());
        } else {
            for (int i = 0; i < banks.size(); i++) {
                System.out.println(i + ": " + banks.get(i));
            }
            int bankAccountID = inputReaderUtil.readInt(StateEnum.CHOOSE_BANKACCOUNT.getStr());
            // TODO: call a service which can connect whith the IBAN of the bank
            Double amount = inputReaderUtil.readDouble(StateEnum.AMOUNT_DEPOSIT.getStr());
            String description = inputReaderUtil.readString(StateEnum.DESCRIPTION.getStr());
            accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
            accountDAO.addTransaction(userAccount.getEmail(), banks.get(bankAccountID), amount, 0.0, description);
            userAccount = accountDAO.getUserInfo(userAccount.getEmail());
        }
    }

    public void getTransactionInfo() {
        List<Transaction> transactions = accountDAO.getTransaction(userAccount.getEmail());
        System.out.println("Date, Money Amount, Relation, Transaction Tax, Description");
        for (Transaction transaction : transactions) {
            System.out.println(
                    transaction.getDate() + " | "
                            + transaction.getMoneyAmount() + " | "
                            + transaction.getRelation() + " | "
                            + transaction.getTaxAmount() + " | "
                            + transaction.getDescription());
        }
    }

    public void getAccountInfo() {
        System.out.println("Info on " + userAccount.getName() + ":");
        System.out.println("Email: " + userAccount.getEmail());
        System.out.println("Money credited on the account: " + userAccount.getMoneyAmount());
    }

}
