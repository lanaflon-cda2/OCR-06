package com.paymybuddy.transferapps.service;

import com.paymybuddy.transferapps.domain.*;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MoneyTransferService extends MainService {

    @Autowired
    protected UserAccountRepository userAccountRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    public boolean addABankAccount(BankAccount bankAccount) {
        // TODO: create a service in order to verify the IBAN
        if (bankAccountRepository.findByAccountIban(bankAccount.getAccountIban()).isEmpty()) {
            bankAccount.setEmail(MyAppUserDetailsService.currentUserEmail());
            bankAccountRepository.save(bankAccount);
            return true;
        } else {
            log.error("This IBAN has already been added");
            return false;
        }
    }

    public void withDrawMoneyFromBankAndAddOnTheAccount(Deposit deposit) {
        //TODO: make contact with the bank to have permission to withdraw
        if (getUserAccountSession().getMoneyAmount() + deposit.getAmount() < 10000) {
            getUserAccountSession().setMoneyAmount(getUserAccountSession().getMoneyAmount() + deposit.getAmount());
            userAccountRepository.save(getUserAccountSession());
            Transaction transaction = new Transaction(
                    false,
                    deposit.getDescription(),
                    deposit.getAmount(),
                    getUserAccountSession().getEmail(),
                    deposit.getAccountName(),
                    Timestamp.from(Instant.now()),
                    0.0);
            transactionRepository.save(transaction);
        } else {
            log.error("You have too much money on your account");
        }
    }

    public void depositMoneyToBankAccount(Deposit deposit) {
        if (getUserAccountSession().getMoneyAmount() > deposit.getAmount()) {
            getUserAccountSession().setMoneyAmount(getUserAccountSession().getMoneyAmount() - deposit.getAmount());
            userAccountRepository.save(getUserAccountSession());
            //TODO: make contact with the bank in order to complete the transaction
            Transaction transaction = new Transaction(
                    true,
                    deposit.getDescription(),
                    -deposit.getAmount(),
                    getUserAccountSession().getEmail(),
                    deposit.getAccountName(),
                    Timestamp.from(Instant.now()),
                    0.0);
            transactionRepository.save(transaction);
        } else {
            log.error("Not enough money on your account");
        }
    }

    public void sendMoneyToARelative(SendMoney sendMoney) {
        double amount = 0.95 * sendMoney.getAmount();
        double taxApps = 0.05 * sendMoney.getAmount();
        //debit the account of the sender
        if (getUserAccountSession().getMoneyAmount() > sendMoney.getAmount()) {
            if(!userAccountRepository.findByEmail(sendMoney.getRelativeEmail()).isEmpty()){
                UserAccount relativeUserAccount = userAccountRepository.findByEmail(sendMoney.getRelativeEmail()).stream().findFirst().get();
                if (relativeUserAccount.getMoneyAmount() + amount <= 10000) {
                    getUserAccountSession().setMoneyAmount(getUserAccountSession().getMoneyAmount() - amount);
                    userAccountRepository.save(getUserAccountSession());
                    //credit the account of the receiver
                    relativeUserAccount.setMoneyAmount(relativeUserAccount.getMoneyAmount() + amount);
                    userAccountRepository.save(relativeUserAccount);
                    //debit the account 5% of the amount of the transaction  and deposit on the account of the company PayMyBuddy
                    //TODO: make contact with the bank in order to complete the transaction
                    getUserAccountSession().setMoneyAmount(getUserAccountSession().getMoneyAmount() - taxApps);
                    userAccountRepository.save(getUserAccountSession());
                    //recording the transaction
                    Transaction transaction = new Transaction(
                            true,
                            sendMoney.getDescription(),
                            -amount,
                            getUserAccountSession().getEmail(),
                            sendMoney.getRelativeEmail(),
                            Timestamp.from(Instant.now()),
                            -taxApps);
                    transactionRepository.save(transaction);
                    Transaction transactionInverse = new Transaction(
                            false,
                            sendMoney.getDescription(),
                            amount,
                            sendMoney.getRelativeEmail(),
                            getUserAccountSession().getEmail(),
                            Timestamp.from(Instant.now()),
                            0);
                    transactionRepository.save(transactionInverse);
                } else {
                    log.error("The relative have too much money on his account");
                }
            } else {
                log.error("This email is not recorded in our database");
            }
        } else {
            log.error("Not enough money on your account");
        }
    }

    //Getters

    public List<Transaction> getTransactionInfo() {
        return transactionRepository.findByEmail(getUserAccountSession().getEmail());
    }

    public List<String> getBankAccounts() {
        List<String> bankAccountName = new ArrayList<>();
        for (BankAccount bank : bankAccountRepository.findByEmail(getUserAccountSession().getEmail())) {
            bankAccountName.add(bank.getAccountName());
        }
        return bankAccountName;
    }
}