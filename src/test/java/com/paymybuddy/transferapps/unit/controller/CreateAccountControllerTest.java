package com.paymybuddy.transferapps.unit.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.paymybuddy.transferapps.dto.CreateAccount;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class CreateAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void createAccount() throws Exception {
        this.mockMvc.perform(get("/account/create"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("createAccount", equalTo(new CreateAccount())));
    }

    @Test
    public void creatingAccount() throws Exception {
        CreateAccount createAccount =new CreateAccount();
        createAccount.setEmail("j@test.com");
        createAccount.setConfirmPassword("test");
        createAccount.setName("rer");
        this.mockMvc.perform(post("/account/creating", createAccount))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/"));
    }
}