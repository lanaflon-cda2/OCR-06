package com.paymybuddy.transferapps.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccount {
    String email;
    String name;
    String password;
    String confirmPassword;
}
