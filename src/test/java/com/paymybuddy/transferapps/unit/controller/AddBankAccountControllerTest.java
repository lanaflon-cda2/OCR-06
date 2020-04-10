package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.UserAccount;
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

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class AddBankAccountControllerTest {

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
    public void addingBankAccountController() throws Exception {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName("account1");
        bankAccount.setAccountIban("555444888");
        mvc.perform(post("/userHome/bankAccount/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",bankAccount.getAccountName())
                .param("accountIban",bankAccount.getAccountIban())
                .param("email", bankAccount.getEmail())
                .requestAttr("bankAccount", bankAccount)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
    }
}