package com.paymybuddy.transferapps.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount account = new UserAccount();

    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        account.setDatelog(Timestamp.from(Instant.now()));
        userAccountRepository.save(account);
    }

    @Test
    public void addBankAccountController() throws Exception {
        mockMvc.perform(get("/userHome/bankAccount/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bankAccount", any(BankAccount.class)));
    }


    @Test
    public void addingBankAccountController() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName("account1");
        bankAccount.setAccountIban("555444888");
        String body = (new ObjectMapper()).valueToTree(bankAccount).toString();
        mockMvc.perform(post("/userHome/bankAccount/adding")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
    }
}