package com.paymybuddy.transferapps.dto;

import com.paymybuddy.transferapps.config.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAccount {
    String email;
    String name;
    @ValidPassword
    String password;
    String confirmPassword;
}
