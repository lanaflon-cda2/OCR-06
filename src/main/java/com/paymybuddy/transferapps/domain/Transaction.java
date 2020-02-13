package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Transaction {
    boolean isCredit;
    UserAccount relation;
    double moneyAmount;
    String description;
    LocalDate transactionTimeStamp;
}
