package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccount {
    String accountName;
    String accountIban;
    double accountMoneyAmount;
}
