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
@NoArgsConstructor
@Entity
@Table(name="userAccount")
public class UserAccount {

    @Id
    String email;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    double moneyAmount;
    @Column(nullable = false)
    String password;
    @Column(nullable = false)
    String role;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName="email")
    private Set<BankAccount> bankAccounts;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName="email")
    private Set<Transaction> transactions;

    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name = "email", referencedColumnName="email")
    private Set<RelationEmail> relationEmails;


    public UserAccount(String email, String name, Double moneyAmount, String role, String password) {
        this.email = email;
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.role = role;
        this.password=password;
    }
}
