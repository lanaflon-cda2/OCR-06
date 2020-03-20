package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.dto.Deposit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AddFriendControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void depositMoney() throws Exception {
        this.mockMvc.perform(get("/userHome/friend/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("relative", equalTo(new RelationEmail())));
    }


    @Test
    public void depositingMoney() throws Exception {
        RelationEmail relationEmail = new RelationEmail();
        this.mockMvc.perform(post("/userHome/friend/adding", relationEmail))
                .andExpect(status().isOk())
                .andExpect(redirectedUrl("/userHome"));
    }
}