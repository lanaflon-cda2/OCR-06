package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.repositories.AccountDAO;
import com.paymybuddy.transferapps.domain.Transaction;
import com.paymybuddy.transferapps.domain.UserAccount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
public class AccountService {

    private static final Logger logger = LogManager.getLogger("AccountService");
    private UserAccount userAccount;

    @Autowired
    private AccountDAO accountDAO;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private RelativeEmailRepository relativeEmailRepository;


    public UserAccount getConnection(String email, String password) {
        if (accountDAO.isGoodLogs(email, password)) {
            this.userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
            return userAccount;
        }
        return null;
    }

    public void disconnect() {
        accountDAO.disconnect(userAccount.getEmail());
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

    public void withDrawMoneyFromBankAndAddOnTheAccount(String bankAccountName, Double amount, String description) {
        accountDAO.tradingWithBank(userAccount.getEmail(), bankAccountName, amount);
        Transaction transaction = new Transaction(
                false,
                description,
                amount,
                userAccount.getEmail(),
                bankAccountName,
                Timestamp.from(Instant.now()),
                0.0);
        accountDAO.addTransaction(transaction);
        this.userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
    }

    public void depositMoneyToBankAccount(String bankAccountName, Double amount, String description) {
        accountDAO.tradingWithBank(userAccount.getEmail(), bankAccountName, -amount);
        Transaction transaction = new Transaction(
                true,
                description,
                -amount,
                userAccount.getEmail(),
                bankAccountName,
                Timestamp.from(Instant.now()),
                0.0);
        accountDAO.addTransaction(transaction);
        this.userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
    }

    public void sendMoneyToARelative(String relativeEmail, Double amount, String description) {
        Double taxApps = 0.05 * amount;
        //debit the account of the sender
        accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
        //credit the account of the receiver
        accountDAO.updateMoneyOnAccount(relativeEmail, amount);
        //debit the account 5% of the amount of the transaction  and deposit on the account of the company PayMyBuddy
        accountDAO.tradingWithBank(userAccount.getEmail(), "BankOfPayMyBuddyCompany", taxApps);
        //recording the transaction
        Transaction transaction = new Transaction(
                true,
                description,
                -amount,
                userAccount.getEmail(),
                relativeEmail,
                Timestamp.from(Instant.now()),
                taxApps);
        accountDAO.addTransaction(transaction);
        Transaction transactionInverse = new Transaction(
                false,
                description,
                0.95 * amount,
                userAccount.getEmail(),
                relativeEmail,
                Timestamp.from(Instant.now()),
                taxApps);
        accountDAO.addTransaction(transactionInverse);
        this.userAccount = userAccountRepository.findByEmail(userAccount.getEmail());
    }


    //Getters

    public List<Transaction> getTransactionInfo() {
        return transactionRepository.findByEmail(userAccount.getEmail());
    }

    public UserAccount getAccountInfo() {
        return userAccount;
    }

    public List<String> getBankAccounts() {
        List<String> bankAccountName = new ArrayList<>();
        for (BankAccount bank: bankAccountRepository.findByEmail(userAccount.getEmail())) {
            bankAccountName.add(bank.getAccountName());
        }
        return bankAccountName;
    }

    public List<String> getRelatives() {
        List<String> relativeList = new ArrayList<>();
        for (RelationEmail relative:  relativeEmailRepository.findByEmail(userAccount.getEmail())) {
            relativeList.add(relative.getEmail());
        }
        return relativeList;
    }
}