package com.paymybuddy.transferapps.unit.controller;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.transferapps.dto.CreateAccount;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class CreateAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void createAccount() throws Exception {
        mockMvc.perform(get("/account/create"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("createAccount", any(CreateAccount.class)));
    }

    @Test
    public void creatingAccount() throws Exception {
        CreateAccount createAccount =new CreateAccount();
        createAccount.setEmail("j@test.com");
        createAccount.setPassword("testGoodPass0");
        createAccount.setConfirmPassword("testGoodPass0");
        createAccount.setName("name");
        String body = (new ObjectMapper()).valueToTree(createAccount).toString();
        mockMvc.perform(post("/account/creating")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}