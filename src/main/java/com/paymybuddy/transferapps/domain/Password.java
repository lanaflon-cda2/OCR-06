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
@Entity(name = "Password")
@Table(name="password")
public class Password {

    @Id
    String email;
    @Column
    String password;

    @OneToOne
    @JoinColumn(name = "email")
    private UserAccount userAccount;

    public Password(String email, String password) {
        this.email=email;
        this.password=password;
    }
}
