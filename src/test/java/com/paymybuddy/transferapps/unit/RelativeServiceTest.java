package com.paymybuddy.transferapps.unit;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import com.paymybuddy.transferapps.repositories.RelativeEmailRepository;
import com.paymybuddy.transferapps.repositories.TransactionRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.RelativeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class RelativeServiceTest {


    @Mock
    private RelativeEmailRepository relativeEmailRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @Captor
    private ArgumentCaptor<RelationEmail> acUserAccount;


    private UserAccount userAccount;
    private RelationEmail relationEmail;


    @InjectMocks
    RelativeService relativeService;

    @BeforeEach
    public void setup() {
        acUserAccount = ArgumentCaptor.forClass(RelationEmail.class);
        userAccount = new UserAccount();
        userAccount.setMoneyAmount(100);
        userAccount.setEmail("test@Mock.com");
        userAccount.setName("John");
        relationEmail = new RelationEmail();
        relationEmail.setId(0L);
        relationEmail.setRelativeEmail("a@guy.com");
        Optional<UserAccount> userAccountOptional = Optional.of(userAccount);
        List<RelationEmail> relatives = new ArrayList<>();
        relatives.add(relationEmail);
        when(userAccountRepository.findByEmail(any())).thenReturn(userAccountOptional);
        when(relativeEmailRepository.findByEmail(any())).thenReturn(relatives);
    }

    @Test
    public void returnGoodAmountOfMoneyDuringWithdrawFromBank() {
        //ARRANGE
        //ACT
        relativeService.addAFriend(relationEmail);
        //ASSERT
        verify(relativeEmailRepository).save(acUserAccount.capture());
        RelationEmail result = acUserAccount.getValue();
        assertThat(result.getRelativeEmail()).isEqualTo("a@guy.com");
        assertThat(result.getEmail()).isEqualTo("test@Mock.com");
    }

    @Test
    public void returnGoodNumberOfRelative() {
        //ARRANGE
        List<RelationEmail> relationEmails = new ArrayList<>();
        relationEmails.add(relationEmail);
        relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("another@guy.com");
        relationEmail.setId(40L);
        relationEmails.add(relationEmail);
        relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("yetanother@guy.com");
        relationEmail.setId(50L);
        relationEmails.add(relationEmail);
        when(relativeEmailRepository.findByEmail(anyString())).thenReturn(relationEmails);
        //ACT
        List<String> result =relativeService.getRelatives();
        //ASSERT
        assertThat(result).hasSize(3);
    }
}