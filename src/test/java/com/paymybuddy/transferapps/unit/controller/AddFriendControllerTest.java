package com.paymybuddy.transferapps.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyList;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class AddFriendControllerTest {

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
    public void addFriendController() throws Exception {
        mockMvc.perform(get("/userHome/friend/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("relative", any(RelationEmail.class)));
    }


    @Test
    public void addingFriendController() throws Exception {
        RelationEmail relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("friend@test.com");
        String body = (new ObjectMapper()).valueToTree(relationEmail).toString();
        mockMvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .content(body)
        )
                .andExpect(status().is3xxRedirection());
    }
}