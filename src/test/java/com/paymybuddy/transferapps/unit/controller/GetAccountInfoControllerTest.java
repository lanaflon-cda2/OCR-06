package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GetAccountInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void depositMoney() throws Exception {
        this.mockMvc.perform(get("/userHome/accountInfo"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userAccounts", equalTo(new UserAccount())))
                .andExpect(model().attribute("relatives", equalTo(anyCollection())))
                .andExpect(model().attribute("bankAccounts", equalTo(anyList())));
    }
}