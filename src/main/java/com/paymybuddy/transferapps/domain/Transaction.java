package com.paymybuddy.transferapps.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Transaction")
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;


    @Column
    Boolean sendingOrReceiving;
    @Column
    String description;
    @Column
    double amount;
    @Column
    String email;
    @Column
    String relativeEmail;
    @Column
    Timestamp date;
    @Column
    double perceiveAmountForApp;


    @ManyToOne
    @JoinColumn(name = "user_email")
    UserAccount userAccount;

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
