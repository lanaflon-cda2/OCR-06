package com.paymybuddy.transferapps.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Transaction")
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue
    private Long id;
    /*
        @ManyToOne
        @JoinColumn(name = "user_email")
        UserAccount userAccount;
        */

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
