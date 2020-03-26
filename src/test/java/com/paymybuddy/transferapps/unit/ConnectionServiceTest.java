package com.paymybuddy.transferapps.unit;


import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.ConnectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class ConnectionServiceTest {



    @Mock
    private UserAccountRepository userAccountRepository;

    private UserAccount userAccount;
    private Logs logs;
    private CreateAccount createAccount;


    @InjectMocks
    ConnectionService connectionService = new ConnectionService();

    @BeforeEach
    public void setup() {
        logs = new Logs();
        createAccount = new CreateAccount();
        userAccount = new UserAccount();
        userAccount.setMoneyAmount(100);
        userAccount.setDatelog(Timestamp.from(Instant.now()));
        userAccount.setEmail("test@Mock.com");
        userAccount.setName("John");
        userAccount.setPassword("test");
        MockitoAnnotations.initMocks(this);
    }
/*
    @Test
    public void returnGoodUserAccountAfterEnterGoodLogs() {
        //ARRANGE
        logs.setEmail("test@Mock.com");
        logs.setPassword("test");
        when(userAccountRepository.findByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(userAccount));
        when(passwordRepository.findFirstByEmail(anyString())).thenReturn(password);

        //ACT
        UserAccount userAccount1 = connectionService.getConnection(logs);
        //ASSERT
        assertThat(userAccount1.getEmail()).isEqualTo("test@Mock.com");
        assertThat(userAccount1.getMoneyAmount()).isEqualTo(100);
        assertThat(userAccount1.getDatelog()).isAfter(Timestamp.from(Instant.ofEpochMilli(0)));
        verify(userAccountRepository, times(2)).findByEmail(anyString());
    }

    @Test
    public void returnNullAfterEnterWrongLogs() {
        //ARRANGE
        logs.setEmail("test@Mock.com");
        logs.setPassword("test");
        when(userAccountRepository.findByEmail(anyString())).thenReturn(java.util.Optional.empty());

        //ACT
        UserAccount userAccount1 = connectionService.getConnection(logs);
        //ASSERT
        assertThat(userAccount1).isNull();
        verify(userAccountRepository, times(1)).findByEmail(anyString());
    }

    @Test
    public void ableToSaveTheNewAccountAfterEnterParameters() {
        //ARRANGE
        createAccount.setEmail("test@Mock.com");
        createAccount.setName("John");
        createAccount.setConfirmPassword("test");
        createAccount.setPassword("test");

        //ACT
        connectionService.createAnAccount(createAccount);

        //ASSERT
        verify(userAccountRepository, times(1)).save(any());
        verify(passwordRepository, times(1)).save(any());
    }

    @Test
    public void notAbleToSaveTheNewAccountAfterEnterWrongParameters() {
        //ARRANGE
        createAccount.setEmail("test@Mock.com");
        createAccount.setName("John");
        createAccount.setConfirmPassword("test");
        createAccount.setPassword("wrongtest");

        //ACT
        connectionService.createAnAccount(createAccount);

        //ASSERT
        verify(userAccountRepository, times(0)).findByEmail(anyString());
        verify(passwordRepository, times(0)).findFirstByEmail(anyString());
    }

    @Test
    public void updateDatelogAfterDiconnect() {
        //ARRANGE
        connectionService.setUserAccountSession(userAccount);
        //ACT
        connectionService.disconnect();
        //ASSERT
        assertThat(connectionService.getUserAccountSession()).isNull();
    }*/
}