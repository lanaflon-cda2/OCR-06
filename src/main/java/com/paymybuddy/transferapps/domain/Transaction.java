package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Transaction {
    String isCredit;
    String relative;
    double moneyAmount;
    double taxAmount;
    String description;
    Timestamp date;
}
