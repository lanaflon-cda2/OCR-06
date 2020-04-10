package com.paymybuddy.transferapps.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DepositControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private UserAccountRepository userAccountRepository;

    private UserAccount account = new UserAccount();

    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        userAccountRepository.save(account);
    }

    @Test
    public void withdrawController() throws Exception {
        mvc.perform(get("/userHome/depositMoney/deposit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("depositMoney", any(Deposit.class)))
                .andExpect(model().attribute("bankAccounts", any(ArrayList.class)));
    }


    @Test
    public void withdrawingController() throws Exception {
        Deposit deposit = new Deposit();
        deposit.setAccountName("myAccount");
        deposit.setAmount(20);
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", deposit.getAccountName())
                .param("description", deposit.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", deposit)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
    }
}