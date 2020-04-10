package com.paymybuddy.transferapps.integration;

import com.paymybuddy.transferapps.domain.Transaction;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.TransactionRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import static org.hamcrest.Matchers.any;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InfoControllersTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    private UserAccount account = new UserAccount();
    private Transaction transaction = new Transaction();
    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        userAccountRepository.save(account);
        transaction.setAmount(50);
        transaction.setDate(Timestamp.from(Instant.now()));
        transaction.setEmail("test@test.com");
        transaction.setRelativeEmail("other");
        transaction.setPerceiveAmountForApp(0);
        transaction.setSendingOrReceiving(true);
        transaction.setId(5L);
        transactionRepository.save(transaction);
    }

    @Test
    public void userAccountInfoControllers() throws Exception {
        this.mockMvc.perform(get("/userHome/profile"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userAccount", any(UserAccount.class)))
                .andExpect(model().attribute("relatives",  any(ArrayList.class)))
                .andExpect(model().attribute("bankAccounts",  any(ArrayList.class)));
    }
}
