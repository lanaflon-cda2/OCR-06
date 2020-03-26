package com.paymybuddy.transferapps.integration.service;

import com.paymybuddy.transferapps.controllers.AddBankAccountControllers;
import com.paymybuddy.transferapps.controllers.CreateAccountControllers;
import com.paymybuddy.transferapps.controllers.LogControllers;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddBankAccountControllersTestIT {


    @Autowired
    BankAccountRepository bankAccountRepository;
    @Autowired
    AddBankAccountControllers addBankAccountControllers;
    @Autowired
    CreateAccountControllers createAccountControllers;
    @Autowired
    LogControllers logControllers;

    @BeforeEach
    public void setUp() {
        CreateAccount createAccount = new CreateAccount();
        createAccount.setName("test");
        createAccount.setEmail("test@i.com");
        createAccount.setPassword("pass");
        createAccount.setConfirmPassword("pass");
        //createAccountControllers.creatingAccount(createAccount);
        Logs logs = new Logs();
        logs.setPassword("pass");
        logs.setEmail("test@i.com");
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
