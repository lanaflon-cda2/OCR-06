package com.paymybuddy.transferapps.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "bankAccount")
public class BankAccount {
    @Id
    String accountIban;
    @Column(nullable = false)
    String accountName;
    @Column()
    String email;


}
