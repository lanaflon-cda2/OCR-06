package com.paymybuddy.transferapps.integration;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@WithMockUser(authorities = "ADMIN", username = "test@test.com")
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SendMoneyTestIT {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;
    @Autowired
    private RelativeEmailRepository relativeEmailRepository;

    @Autowired
    private MockMvc mvc;

    private UserAccount account = new UserAccount();
    private UserAccount account2 = new UserAccount();
    private RelationEmail relationEmail = new RelationEmail();
    private BankAccount bankAccount = new BankAccount();

    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        account.setMoneyAmount(50);
        userAccountRepository.save(account);

        account2.setEmail("friend@test.com");
        account2.setName("friend");
        account2.setPassword("password");
        account2.setRole("ADMIN");
        account2.setMoneyAmount(50);
        userAccountRepository.save(account2);

        relationEmail.setId(1L);
        relationEmail.setRelativeEmail("friend@test.com");
        relationEmail.setEmail("test@test.com");
        relativeEmailRepository.save(relationEmail);

        bankAccount.setAccountIban("5555");
        bankAccount.setAccountName("myAccount");
        bankAccount.setEmail("test@test.com");
        bankAccountRepository.save(bankAccount);

    }

    @Test
    public void accessSendMoneyFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/transfer")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("amount")
                .sessionAttr("dto", new SendMoney())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("Transfer"));
    }


    @Test
    public void sendMoneyWithSuccess() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/transfer"));
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(30);
    }
    @Test
    public void errorIfTooMuchMoneyInTheRelativeAccount() throws Exception {
        account2.setMoneyAmount(9990);
        userAccountRepository.save(account2);
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/transfer"));
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(50);
        assertThat(userAccountRepository.findByEmail("friend@test.com").get().getMoneyAmount()).isEqualTo(9990);
    }
    @Test
    public void errorIfNotEnoughMoneyOnTheAccount() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "200.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/transfer"));
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(50);
        assertThat(userAccountRepository.findByEmail("friend@test.com").get().getMoneyAmount()).isEqualTo(50);
    }

    @Test
    public void errorIfNoExistingRelation() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("noname@test.com");
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "10.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/transfer"));
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(50);
        assertThat(userAccountRepository.findByEmail("friend@test.com").get().getMoneyAmount()).isEqualTo(50);
    }

    @Test
    public void sendMoney2timesWithSuccess() throws Exception {
        SendMoney sendMoney = new SendMoney();
        sendMoney.setRelativeEmail("friend@test.com");
        sendMoney.setAmount(20);
        sendMoney.setDescription("a good transfer");
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/transfer"));
        assertThat(bankAccountRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(bankAccountRepository.findByAccountIban("5555")).isPresent();
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(30);

        sendMoney.setAmount(10);
        mvc.perform(post("/userHome/sendMoney/sending")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail",sendMoney.getRelativeEmail())
                .param("description",sendMoney.getDescription())
                .param("amount", "20.00")
                .requestAttr("deposit", sendMoney)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/transfer"));
        assertThat(userAccountRepository.findByEmail("test@test.com").get().getMoneyAmount()).isEqualTo(10);
    }
}
