package com.paymybuddy.transferapps.integration.service;

import com.paymybuddy.transferapps.controllers.AddBankAccountControllers;
import com.paymybuddy.transferapps.controllers.CreateAccountControllers;
import com.paymybuddy.transferapps.controllers.LogControllers;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddBankAccountControllersTestIT {


    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    AddBankAccountControllers addBankAccountControllers;


    @BeforeEach
    public void setUp() {

    }

    @Test
    public void addBankAccountWithCorrectData() {
        //ARRANGE
        BankAccount bankAccount0 = new BankAccount();
        bankAccount0.setAccountName("bank");
        bankAccount0.setAccountIban("IbanTest");
        //ACT
        addBankAccountControllers.addingABankAccount(bankAccount0);
        //ASSERT
        Optional<BankAccount> bankAccount = bankAccountRepository.findByAccountIban("IbanTest");
        assertThat(bankAccount.get().getAccountIban()).isEqualTo("IbanTest");
        assertThat(bankAccount.get().getEmail()).isEqualTo("test@i.com");
    }

}
