package com.paymybuddy.transferapps.unit.controller;


import com.paymybuddy.transferapps.config.SecurityConfiguration;
import com.paymybuddy.transferapps.controllers.AddBankAccountControllers;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
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
@WebMvcTest(
        controllers = AddBankAccountControllers.class
)
@ContextConfiguration(
        classes = SecurityConfiguration.class
)
public class AddFriendControllerTest extends AbstractIT {

    @MockBean
    MoneyTransferService service;

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private RelativeEmailRepository relativeEmailRepository;

    @Autowired
    private MockMvc mvc;

    private UserAccount account = new UserAccount();
    private UserAccount relationAccount = new UserAccount();
    private UserAccount relationAccount2 = new UserAccount();

    @BeforeEach
    public void setup() {
        account.setEmail("test@test.com");
        account.setName("user");
        account.setPassword("password");
        account.setRole("ADMIN");
        userAccountRepository.save(account);
        relationAccount.setEmail("friend@test.com");
        relationAccount.setName("user2");
        relationAccount.setPassword("anotherPass");
        relationAccount.setRole("USER");
        userAccountRepository.save(relationAccount);
    }

    @Test
    public void fillFriendFormWithSuccess() throws Exception {
        mvc.perform(get("/userHome/friend/add")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("relativeEmail", "friend@test.com")
                .content("friend@test.com")
                .sessionAttr("dto", new RelationEmail())
        )
                .andExpect(status().isOk())
                .andExpect(view().name("FriendAdd"));
    }

    @Test
    public void postNewFriendWithSuccess() throws Exception {
        RelationEmail relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("friend@test.com");
        mvc.perform(post("/userHome/friend/adding")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("email", relationEmail.getEmail())
                .param("relativeEmail", relationEmail.getRelativeEmail())
                .requestAttr("relationEmail", relationEmail)
                .contentType(MediaType.APPLICATION_XHTML_XML)
        )
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/userHome"));
        assertThat(relativeEmailRepository.findByEmail("test@test.com")).hasSize(1);
        assertThat(relativeEmailRepository.findByEmail("test@test.com").get(0).getRelativeEmail()).isEqualTo("friend@test.com");
    }
}
