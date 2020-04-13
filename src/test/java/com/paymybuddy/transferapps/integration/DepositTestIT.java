package com.paymybuddy.transferapps.integration;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.Transaction;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import com.paymybuddy.transferapps.repositories.TransactionRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
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


public class DepositTestIT extends AbstractIT{


    private UserAccount account = new UserAccount();
    private BankAccount bankAccount = new BankAccount();

    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        account.setMoneyAmount(50);
        userAccountRepository.save(account);
        bankAccount.setAccountIban("5555");
        bankAccount.setAccountName("myAccount");
        bankAccount.setEmail("test@test.com");
        bankAccountRepository.save(bankAccount);

    }

    @Test
    public void accessDepositFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/depositMoney/deposit")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount", "50")
                .content("amount")
                .sessionAttr("dto", new Deposit())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("depositMoney"));
    }

    @Test
    public void accessWithdrawFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/withdrawMoney/withdraw")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("amount", "50")
                .content("amount")
                .sessionAttr("dto", new Deposit())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("withdrawMoney"));
    }

    @Test
    public void depositMoneyWithSuccess() throws Exception {
        Deposit deposit = new Deposit();
        deposit.setAccountName("myAccount");
        deposit.setAmount(20);
        deposit.setDescription("a description");
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",deposit.getAccountName())
                .param("description",deposit.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", deposit)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(transactionRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(transactionRepository.findByEmail("test@test.com").get(0).getDescription()).isEqualTo(deposit.getDescription());
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(30);
    }

    @Test
    public void depositMoney2timesWithSuccess() throws Exception {
        Deposit deposit = new Deposit();
        deposit.setAccountName("myAccount");
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",deposit.getAccountName())
                .param("description",deposit.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", deposit)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));

        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(30);

        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",deposit.getAccountName())
                .param("description",deposit.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", deposit)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(10);
    }

    @Test
    public void depositMoreMoneyThanItsOwnAndReturnError() throws Exception {
        Deposit deposit = new Deposit();
        deposit.setAccountName("myAccount");
        mvc.perform(post("/userHome/depositMoney/depositing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",deposit.getAccountName())
                .param("description",deposit.getDescription())
                .param("amount", "100.00")
                .requestAttr("deposit", deposit)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection());

        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(50);
        assertThat(transactionRepository.findAll()).isEmpty();
    }
}