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
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_email")
    UserAccount userAccount;

    @Column(nullable = false)
    String accountName;
    @Column(nullable = false)
    String accountIban;
    @Column(nullable = false)
    double accountMoneyAmount;

}
