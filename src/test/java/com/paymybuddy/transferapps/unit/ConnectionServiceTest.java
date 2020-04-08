package com.paymybuddy.transferapps.unit;


import com.mysql.cj.log.Slf4JLogger;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.ConnectionService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Mock
    private Slf4JLogger slf4j;

    private UserAccount userAccount;
    private CreateAccount createAccount;
    @Captor
    private ArgumentCaptor<UserAccount> acUserAccount;

    @InjectMocks
    ConnectionService connectionService = new ConnectionService();

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @BeforeEach
    public void setup() {
        acUserAccount = ArgumentCaptor.forClass(UserAccount.class);
        createAccount = new CreateAccount();
        createAccount.setName("name");
        createAccount.setConfirmPassword("pass");
        createAccount.setPassword("pass");
        createAccount.setEmail("test@Mock.com");
        MockitoAnnotations.initMocks(this);
        when(userAccountRepository.findByEmail(any())).thenReturn(java.util.Optional.ofNullable(userAccount));
    }

    @Test
    public void returnGoodUserAccountAfterEnterGoodLogs() {
        //ACT
        connectionService.createAnAccount(createAccount);
        //ASSERT
        verify(userAccountRepository).save(acUserAccount.capture());
        assertThat(encoder.matches("pass", acUserAccount.getValue().getPassword())).isTrue();
        assertThat(acUserAccount.getValue().getName()).isEqualTo("name");
    }

    @Test
    public void returnErrorAfterEnterBadLogs() {
        createAccount.setConfirmPassword("fauxPass");
        //ACT
        connectionService.createAnAccount(createAccount);
        //ASSERT
        verify(userAccountRepository, times(0)).save(any());
    }

}