package com.paymybuddy.transferapps.dto;

import com.paymybuddy.transferapps.config.ValidPassword;
import lombok.Getter;
import lombok.Setter;

/**
 * CreateAccount is a DTO which value "password" is verify to matches the security conditions before being
 * encrypted. Then the data is registered as a UserAccount object
 */


@Getter
@Setter
public class CreateAccount {
    String email;
    String name;
    @ValidPassword
    String password;
    String confirmPassword;
}
