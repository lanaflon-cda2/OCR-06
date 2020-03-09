package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deposit {
    String bankAccountName;
    String description;
    double amount;
}
