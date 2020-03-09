package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.*;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.repositories.AccountDAO;
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
    @Autowired
    private PasswordRepository passwordRepository;

    public UserAccount getConnection(Logs logs) {
        String email = logs.getEmail();
        String password = logs.getPassword();
        if (passwordRepository.findFirstByEmail(email).equals(password)) {
            this.userAccount = userAccountRepository.findFirstByEmail(userAccount.getEmail());
            return userAccount;
        }
        return null;
    }

    public void disconnect() {
        userAccount.setDatelog(null);
        userAccountRepository.save(userAccount);
        this.userAccount = null;
    }

    public Boolean isConnected() {
        return userAccount.getDatelog().after(new Timestamp(System.currentTimeMillis() - 1000 * 60 * 4));
    }

    public Boolean createAnAccount(CreateAccount createAccount) {
        userAccountRepository.save(new UserAccount(createAccount.getEmail(), createAccount.getName(), 0.0, Timestamp.from(Instant.now())));
        passwordRepository.save(new Password(createAccount.getEmail(), createAccount.getPassword()));
        return true;
    }

    public void addAFriend(RelationEmail relationEmail) {
        relationEmail.setEmail(userAccount.getEmail());
        relativeEmailRepository.save(relationEmail);
    }

    public void addABankAccount(String bankName) {
        // TODO: create a service in order to entering the IBAN
        int character = (int) (Math.random() * 20);
        String iban = String.valueOf(character);
        bankAccountRepository.save(new BankAccount(userAccount.getEmail(), bankName, iban));
    }

    public void withDrawMoneyFromBankAndAddOnTheAccount(Deposit deposit) {
        userAccount.setMoneyAmount(userAccount.getMoneyAmount() + deposit.getAmount());
        userAccountRepository.save(userAccount);
        Transaction transaction = new Transaction(
                false,
                deposit.getDescription(),
                deposit.getAmount(),
                userAccount.getEmail(),
                deposit.getBankAccountName(),
                Timestamp.from(Instant.now()),
                0.0);
        transactionRepository.save(transaction);
        this.userAccount = userAccountRepository.findFirstByEmail(userAccount.getEmail());
    }

    public void depositMoneyToBankAccount(Deposit deposit) {
        userAccount.setMoneyAmount(userAccount.getMoneyAmount() - deposit.getAmount());
        userAccountRepository.save(userAccount);
        Transaction transaction = new Transaction(
                true,
                deposit.getDescription(),
                -deposit.getAmount(),
                userAccount.getEmail(),
                deposit.getBankAccountName(),
                Timestamp.from(Instant.now()),
                0.0);
        transactionRepository.save(transaction);
        this.userAccount = userAccountRepository.findFirstByEmail(userAccount.getEmail());
    }

    public void sendMoneyToARelative(SendMoney sendMoney) {
        Double amount = sendMoney.getAmount();
        Double taxApps = 0.05 * amount;
        //debit the account of the sender
        accountDAO.updateMoneyOnAccount(userAccount.getEmail(), -amount);
        //credit the account of the receiver
        accountDAO.updateMoneyOnAccount(sendMoney.getRelativeEmail(), amount);
        //debit the account 5% of the amount of the transaction  and deposit on the account of the company PayMyBuddy
        accountDAO.tradingWithBank(userAccount.getEmail(), "BankOfPayMyBuddyCompany", taxApps);
        //recording the transaction
        Transaction transaction = new Transaction(
                true,
                sendMoney.getDescription(),
                -amount,
                userAccount.getEmail(),
                sendMoney.getRelativeEmail(),
                Timestamp.from(Instant.now()),
                taxApps);
        accountDAO.addTransaction(transaction);
        Transaction transactionInverse = new Transaction(
                false,
                sendMoney.getDescription(),
                0.95 * amount,
                userAccount.getEmail(),
                sendMoney.getRelativeEmail(),
                Timestamp.from(Instant.now()),
                taxApps);
        accountDAO.addTransaction(transactionInverse);
        this.userAccount = userAccountRepository.findFirstByEmail(userAccount.getEmail());
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
        for (BankAccount bank : bankAccountRepository.findByEmail(userAccount.getEmail())) {
            bankAccountName.add(bank.getAccountName());
        }
        return bankAccountName;
    }

    public List<String> getRelatives() {
        List<String> relativeList = new ArrayList<>();
        for (RelationEmail relative : relativeEmailRepository.findByEmail(userAccount.getEmail())) {
            relativeList.add(relative.getEmail());
        }
        return relativeList;
    }
}