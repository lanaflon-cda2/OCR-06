package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class BankCompany {
    String companyName;
    List<BankAccount> bankAccounts;
}
