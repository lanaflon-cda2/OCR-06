package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddBankAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void depositMoney() throws Exception {
        this.mockMvc.perform(get("/userHome/bankAccount/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("bankAccount", equalTo(new BankAccount())));
    }


    @Test
    public void depositingMoney() throws Exception {
        BankAccount relationEmail = new BankAccount();
        this.mockMvc.perform(post("/userHome/bankAccount/adding", relationEmail))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/userHome"));
    }
}