package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UserAccount {
    List<BankAccount> bankAccounts;
    String email;
    String name;
    String password;
    String accountlog;
    List<String> connectionEmails;
    List<Transaction> historicTransactions;
}
