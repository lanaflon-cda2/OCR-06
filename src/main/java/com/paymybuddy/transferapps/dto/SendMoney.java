package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendMoney {
    String relativeEmail;
    String description;
    double amount;
}
