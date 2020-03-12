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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;

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
    private ArgumentCaptor<UserAccount> acUserAccount;


    private UserAccount userAccount;

    private RelationEmail relationEmail;


    @InjectMocks
    RelativeService relativeService = new RelativeService();

    @BeforeEach
    public void setup() {
        acUserAccount = ArgumentCaptor.forClass(UserAccount.class);
        userAccount = new UserAccount();
        userAccount.setMoneyAmount(100);
        userAccount.setDatelog(Timestamp.from(Instant.now()));
        userAccount.setEmail("test@Mock.com");
        userAccount.setName("John");
        relativeService.setUserAccountSession(userAccount);
        relationEmail.setId(0L);
        relationEmail.setRelativeEmail("a@guy.com");
    }

    @Test
    public void returnGoodAmountOfMoneyDuringWithdrawFromBank() {
        //ARRANGE

        when(userAccountRepository.findByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(userAccount));
        //ACT
        relativeService.addAFriend(relationEmail);
        //ASSERT
        verify(userAccountRepository).save(acUserAccount.capture());
        UserAccount result = acUserAccount.getValue();
        assertThat(result.getMoneyAmount()).isEqualTo(155.55);
        assertThat(result.getEmail()).isEqualTo("test@Mock.com");
    }

}