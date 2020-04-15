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
import java.util.List;


/**
 * -addABankAccount(BankAccount) link a bankAccount IBAN to the userAccount in order to withdrawing and depositing money
 * -withDrawMoneyFromBankAndAddOnTheAccount(Deposit) withdraw money from a bank account to the userAccount
 * -depositMoneyToBankAccount(Deposit) deposit money from userAccount to a bank account
 * -sendMoneyToARelative(SendMoney) send money from the current User account to a relative of the User with 5% tax
 */


@Service
@Slf4j
public class MoneyTransferService {

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

    public boolean withDrawMoneyFromBankAndAddOnTheAccount(Deposit deposit) {
        //TODO: make contact with the bank to have permission to withdraw
        UserAccount userAccount = userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get();
        if (userAccount.getMoneyAmount() + deposit.getAmount() < 10000) {
            userAccount.setMoneyAmount(userAccount.getMoneyAmount() + deposit.getAmount());
            userAccountRepository.save(userAccount);
            Transaction transaction = new Transaction(
                    false,
                    deposit.getDescription(),
                    deposit.getAmount(),
                    userAccount.getEmail(),
                    deposit.getAccountName(),
                    Timestamp.from(Instant.now()),
                    0.0);
            transactionRepository.save(transaction);
            return true;
        } else {
            log.error("You have too much money on your account");
            return false;
        }
    }

    public boolean depositMoneyToBankAccount(Deposit deposit) {
        UserAccount userAccount = userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get();
        if (userAccount.getMoneyAmount() > deposit.getAmount()) {
            userAccount.setMoneyAmount(userAccount.getMoneyAmount() - deposit.getAmount());
            userAccountRepository.save(userAccount);
            //TODO: make contact with the bank in order to complete the transaction
            Transaction transaction = new Transaction(
                    true,
                    deposit.getDescription(),
                    -deposit.getAmount(),
                    userAccount.getEmail(),
                    deposit.getAccountName(),
                    Timestamp.from(Instant.now()),
                    0.0);
            transactionRepository.save(transaction);
            return true;
        } else {
            log.error("Not enough money on your account");
            return false;
        }
    }

    public boolean sendMoneyToARelative(SendMoney sendMoney) {
        UserAccount userAccount = userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get();
        double amount = Math.ceil(95 * sendMoney.getAmount());
        double taxApps = Math.floor(5 * sendMoney.getAmount());
        //debit the account of the sender
        if (userAccount.getMoneyAmount() > sendMoney.getAmount()) {
            if (userAccountRepository.findByEmail(sendMoney.getRelativeEmail()).isPresent()) {
                UserAccount relativeUserAccount = userAccountRepository.findByEmail(sendMoney.getRelativeEmail()).get();
                if (relativeUserAccount.getMoneyAmount() + amount / 100 <= 10000) {
                    userAccount.setMoneyAmount(userAccount.getMoneyAmount() - amount / 100);
                    userAccountRepository.save(userAccount);
                    //credit the account of the receiver
                    relativeUserAccount.setMoneyAmount(relativeUserAccount.getMoneyAmount() + amount / 100);
                    userAccountRepository.save(relativeUserAccount);
                    //debit the account 5% of the amount of the transaction  and deposit on the account of the company PayMyBuddy
                    //TODO: make contact with the bank in order to complete the transaction
                    userAccount.setMoneyAmount((userAccount.getMoneyAmount() * 100 - taxApps) / 100);
                    userAccountRepository.save(userAccount);
                    //recording the transaction
                    Transaction transaction = new Transaction(
                            true,
                            sendMoney.getDescription(),
                            -amount,
                            userAccount.getEmail(),
                            sendMoney.getRelativeEmail(),
                            Timestamp.from(Instant.now()),
                            -taxApps);
                    transactionRepository.save(transaction);
                    Transaction transactionInverse = new Transaction(
                            false,
                            sendMoney.getDescription(),
                            amount,
                            sendMoney.getRelativeEmail(),
                            userAccount.getEmail(),
                            Timestamp.from(Instant.now()),
                            0);
                    transactionRepository.save(transactionInverse);
                    return true;
                } else {
                    log.error("The relative have too much money on his account");
                    return false;
                }
            } else {
                log.error("This email is not recorded in our database");
                return false;
            }
        } else {
            log.error("Not enough money on your account");
            return false;
        }
    }

    //Getters

    public List<Transaction> getTransactionInfo() {
        return transactionRepository.findByEmail(userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get().getEmail());
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccountRepository.findByEmail(userAccountRepository.findByEmail(
                MyAppUserDetailsService.currentUserEmail()
        )
                .get().getEmail());
    }
}