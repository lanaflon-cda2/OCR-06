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
    Timestamp datelog;
    @Column(nullable = false)
    String roles;

    @OneToMany
    @JoinColumn(name = "email")
    private Set<BankAccount> bankAccounts;

    @OneToMany
    @JoinColumn(name = "email")
    private Set<Transaction> transactions;

    @OneToMany
    @JoinColumn(name = "email")
    private Set<RelationEmail> relationEmails;

    @OneToOne
    @JoinColumn(name = "email")
    private Password password;


    public UserAccount(String email, String name, Double moneyAmount, Timestamp timestamp) {
        this.email = email;
        this.name = name;
        this.moneyAmount = moneyAmount;
        this.datelog = timestamp;
    }
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
