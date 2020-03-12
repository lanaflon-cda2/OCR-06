package com.paymybuddy.transferapps.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(nullable = false)
    Boolean sendingOrReceiving;
    @Column
    String description;
    @Column(nullable = false)
    double amount;
    @Column(nullable = false)
    String email;
    @Column(nullable = false)
    String relativeEmail;
    @Column(nullable = false)
    Timestamp date;
    @Column(nullable = false)
    double perceiveAmountForApp;


    public Transaction(Boolean sendingOrReceiving, String description, double amount, String email, String relativeEmail, Timestamp date, double perceiveAmountForApp) {
        this.sendingOrReceiving = sendingOrReceiving;
        this.description = description;
        this.amount = amount;
        this.email = email;
        this.relativeEmail = relativeEmail;
        this.date = date;
        this.perceiveAmountForApp = perceiveAmountForApp;
    }
}
