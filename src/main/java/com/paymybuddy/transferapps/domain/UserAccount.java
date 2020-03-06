package com.paymybuddy.transferapps.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UserAccount")
@Table(name="CUST", schema="RECORDS")
public class UserAccount {

    @Id
    @GeneratedValue
    private Long id;

    String email;
    @Column
    String name;
    @Column
    double moneyAmount;
    @Column
    Timestamp datelog;

    @OneToMany
    @JoinColumn(name = "email")
    private Set<BankAccount> bankAccounts;

    @OneToMany
    @JoinColumn(name = "email")
    private Set<Transaction> transactions;
}
