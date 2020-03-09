package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "BankAccount")
@Table(name = "bankAccount")
public class BankAccount {
    @Id
    private String email;
    @Column(nullable = false)
    String accountName;
    @Column(nullable = false)
    String accountIban;

    @ManyToOne
    @JoinColumn(name = "email")
    UserAccount userAccount;

    public BankAccount(String email, String bankName, String iban) {
        this.email=email;
        this.accountName=bankName;
        this.accountIban=iban;
    }
}
