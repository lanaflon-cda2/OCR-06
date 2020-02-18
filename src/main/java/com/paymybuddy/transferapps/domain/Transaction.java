package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class Transaction {
    boolean isCredit;
    String relation;
    double moneyAmount;
    double taxAmount;
    String description;
    Timestamp date;
}
