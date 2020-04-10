package com.paymybuddy.transferapps.unit.controller;


import com.paymybuddy.transferapps.domain.BankAccount;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
public class WithdrawControllerTest extends AbstractIT {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private MockMvc mvc;

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
    public void withdrawMoneyWithSuccess() throws Exception {
        Deposit withdraw = new Deposit();
        withdraw.setAccountName("myAccount");
        withdraw.setDescription("a description");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",withdraw.getAccountName())
                .param("description",withdraw.getDescription())
                .param("amount", "20.00")
                .requestAttr("withdraw", withdraw)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
    }

    @Test
    public void returnErrorIfMoneyAmountBreakTheMaximumAmountPossible() throws Exception {
        Deposit withdraw = new Deposit();
        withdraw.setAccountName("myAccount");
        mvc.perform(post("/userHome/withdrawMoney/withdrawing")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("accountName",withdraw.getAccountName())
                .param("description",withdraw.getDescription())
                .param("amount", "11000.00")
                .requestAttr("withdraw", withdraw)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(status().is3xxRedirection());
    }
}
