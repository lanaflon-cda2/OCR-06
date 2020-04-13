package com.paymybuddy.transferapps.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import org.junit.jupiter.api.AfterEach;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class AddBankAccountTestIT extends AbstractIT{

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
    public void fillBankFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/bankAccount/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName", "account1")
                .content("account1")
                .sessionAttr("dto", new BankAccount())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("bankAccountAdd"));
    }

    @Test
    public void postNewBankAccountWithSuccess() throws Exception {
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
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("555444888")).isPresent();
    }

    @Test
    public void post2NewBankAccountWithSuccess() throws Exception {
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

        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("555444888")).isPresent();
        bankAccount.setAccountIban("555555888");
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
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(2);
        assertThat(bankAccountRepository.findByAccountIban("555555888")).isPresent();
    }
}
