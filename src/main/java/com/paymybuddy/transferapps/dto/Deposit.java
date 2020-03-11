package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deposit {
    String accountName;
    String description;
    double amount;
}
