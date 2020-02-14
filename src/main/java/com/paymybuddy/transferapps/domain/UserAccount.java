package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserAccount {
    String email;
    String name;
    double moneyAmount;
    List<BankAccount> bankAccounts;
    List<String> connectionEmails;
    List<Transaction> historicTransactions;
}
