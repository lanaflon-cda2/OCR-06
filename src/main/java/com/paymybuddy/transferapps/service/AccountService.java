package com.paymybuddy.transferapps.service;

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
import java.util.Scanner;


@Service
public class AccountService {
    private static InputReaderUtil inputReaderUtil = new InputReaderUtil();

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
            System.out.println("Hello " + userAccount.getName() + "! Your are connected");
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

    public void addABankAccount() {
        String bankName;
        System.out.println("Please enter a new bank account name");
        bankName = scan.nextLine();
        String iban = new String(new byte[20], Charset.forName("UTF-8"));
        accountDAO.addBankAccount(userAccount.getEmail(), bankName, iban);
    }

    public void getAccountInfo() {
        System.out.println("Info on " + userAccount.getName() + ":");
        System.out.println("Email: " + userAccount.getEmail());
        System.out.println("Money credited on the account: " + userAccount.getMoneyAmount());
    }

    public void depositMoney() {
        Double amount;
        amount = inputReaderUtil.readDouble("Please enter the amount you want to deposit on your account.");
        accountDAO.updateMoneyOnAccount(userAccount.getEmail(), amount);
        userAccount = accountDAO.getUserInfo(userAccount.getEmail());
    }

    public void sendMoneyToARelative() {
        Double amount;
        Double taxApps;
        Double amountRemaining;
        String description;
        int friendID;
        List<String> friends = accountDAO.getRelations(userAccount.getEmail());
        if (friends.isEmpty()) {
            logger.error("Sorry, you have no relatives, please add at least one.");
        } else {
            for (int i = 0; i < friends.size(); i++) {
                System.out.println(i + ": " + friends.get(i));
            }
            friendID = inputReaderUtil.readInt("Please choose the friend by entering the corresponding number.");
            amount = inputReaderUtil.readDouble("Please enter the amount you want to send to him.");
            taxApps = 0.05 * amount;
            amountRemaining = 0.95 * amount;
            System.out.println("You can add a comment for the transfer.");
            description = scan.nextLine();
            accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
            accountDAO.updateMoneyOnAccount(friends.get(friendID), amountRemaining);
            accountDAO.addTransaction(userAccount.getEmail(), friends.get(friendID), amount, taxApps, description);
            userAccount = accountDAO.getUserInfo(userAccount.getEmail());
        }
    }

    public void sendMoneyToBankAccount() {
        Double amount;
        int bankAccountID;
        List<String> banks = accountDAO.getBankAccounts(userAccount.getEmail());
        if (banks.isEmpty()) {
            logger.error("You don't have any bank account registered, please add one.");
        } else {
            for (int i = 0; i < banks.size(); i++) {
                System.out.println(i + ": " + banks.get(i));
            }
            bankAccountID = inputReaderUtil.readInt("Please choose the bank account by entering the corresponding number.");
            // TODO: call a service which can connect whith the IBAN of the bank
            amount = inputReaderUtil.readDouble("Please enter the amount you want to transfer on the bank account.");
            accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
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


}
