package com.paymybuddy.transferapps.integration.service;

import com.paymybuddy.transferapps.controllers.CreateAccountControllers;
import com.paymybuddy.transferapps.controllers.LogControllers;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogsControllersTestIT {


    @Autowired
    UserAccountRepository userAccountRepository;
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
        createAccountControllers.creatingAccount(createAccount);
    }

    @Test
    public void addBankAccountWithCorrectData() {
        //ARRANGE
        Logs logs = new Logs();
        logs.setPassword("pass");
        logs.setEmail("test@i.com");
        //ACT
        logControllers.getConnection(logs);
        //ASSERT
        Optional<UserAccount> userAccount = userAccountRepository.findByEmail("test@i.com");
        assertThat(userAccount.get().getPassword()).isEqualTo("pass");
        assertThat(userAccount.get().getEmail()).isEqualTo("test@i.com");
        assertThat(userAccount.get().getMoneyAmount()).isEqualTo(0);
        assertThat(userAccount.get().getDatelog()).isAfter(new Timestamp(System.currentTimeMillis() - 1000 * 10));
    }

}
