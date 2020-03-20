package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DepositControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void depositMoney() throws Exception {
        this.mockMvc.perform(get("/account/deposit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("depositMoney", equalTo(new Deposit())))
        .andExpect(model().attribute("bankAccounts", equalTo(anyList())));
    }


    @Test
    public void depositingMoney() throws Exception {
        Deposit deposit =new Deposit();
        this.mockMvc.perform(post("/account/depositing", deposit))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/"));
    }
}