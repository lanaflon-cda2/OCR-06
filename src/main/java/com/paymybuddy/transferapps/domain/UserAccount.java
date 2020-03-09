package com.paymybuddy.transferapps.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UserAccount")
@Table(name="userAccount")
public class UserAccount {

    @Id
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

    @OneToOne
    @JoinColumn(name = "email")
    private Password password;

    public UserAccount(String email, String name, Double moneyAmount, Timestamp timestamp) {
        this.email = email;
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.datelog = timestamp;
    }
}
