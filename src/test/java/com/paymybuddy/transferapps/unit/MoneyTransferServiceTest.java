package com.paymybuddy.transferapps.unit;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.domain.Password;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.repositories.BankAccountRepository;
import com.paymybuddy.transferapps.repositories.PasswordRepository;
import com.paymybuddy.transferapps.repositories.TransactionRepository;
import com.paymybuddy.transferapps.repositories.UserAccountRepository;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
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
public class MoneyTransferServiceTest {


    @Mock
    private BankAccountRepository bankAccountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserAccountRepository userAccountRepository;
    @Captor
    private ArgumentCaptor<UserAccount> acUserAccount;
    @Captor
    private ArgumentCaptor<BankAccount> acBankAccount;

    private UserAccount userAccount;

    private SendMoney sendMoney;
    private Deposit deposit;


    @InjectMocks
    MoneyTransferService moneyTransferService = new MoneyTransferService();

    @BeforeEach
    public void setup() {
        acUserAccount = ArgumentCaptor.forClass(UserAccount.class);
        userAccount = new UserAccount();
        userAccount.setMoneyAmount(100);
        userAccount.setDatelog(Timestamp.from(Instant.now()));
        userAccount.setEmail("test@Mock.com");
        userAccount.setName("John");
        deposit = new Deposit();
        deposit.setAccountName("OtherAccount");
        deposit.setAmount(55.55);
        deposit.setDescription("description");
        sendMoney = new SendMoney();
        sendMoney.setAmount(55.55);
        sendMoney.setRelativeEmail("this@guy.com");
        moneyTransferService.setUserAccountSession(userAccount);
    }

    @Test
    public void returnGoodAmountOfMoneyDuringWithdrawFromBank() {
        //ARRANGE

        when(userAccountRepository.findByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(userAccount));
        //ACT
        moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(deposit);
        //ASSERT
        verify(userAccountRepository).save(acUserAccount.capture());
        UserAccount result = acUserAccount.getValue();
        assertThat(result.getMoneyAmount()).isEqualTo(155.55);
        assertThat(result.getEmail()).isEqualTo("test@Mock.com");
    }

    @Test
    public void cancelTransactionBecauseOfMissingMoney() {
        //ARRANGE
        userAccount.setMoneyAmount(10);
        when(userAccountRepository.findByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(userAccount));
        //ACT
        moneyTransferService.depositMoneyToBankAccount(deposit);
        //ASSERT
        verify(userAccountRepository, times(0)).save(any());
        verify(transactionRepository, times(0)).save(any());

    }

    @Test
    public void returnGoodAmountOfMoneyDuringSendingToBankAccount() {
        //ARRANGE

        when(userAccountRepository.findByEmail(anyString())).thenReturn(java.util.Optional.ofNullable(userAccount));
        //ACT
        moneyTransferService.depositMoneyToBankAccount(deposit);
        verify(userAccountRepository).save( acUserAccount.capture());
        UserAccount result = acUserAccount.getValue();
        //ASSERT
        assertThat(result.getMoneyAmount()).isEqualTo(44.45);
        assertThat(result.getEmail()).isEqualTo("test@Mock.com");
    }

    @Test
    public void returnGoodAmountOfMoneyDuringSendingToARelative() {
        //ARRANGE
        UserAccount relativeUserAccount = new UserAccount();
        relativeUserAccount.setName("relative");
        relativeUserAccount.setEmail("r@u.com");
        relativeUserAccount.setMoneyAmount(0);
        when(userAccountRepository.findByEmail(anyString()))
                .thenReturn(java.util.Optional.ofNullable(userAccount))
                .thenReturn(java.util.Optional.of(relativeUserAccount))
                .thenReturn(java.util.Optional.ofNullable(userAccount));
        //ACT
        moneyTransferService.sendMoneyToARelative(sendMoney);
        //ASSERT
        verify(userAccountRepository, times(3)).save(acUserAccount.capture());

        UserAccount result = acUserAccount.getValue();
        assertThat(result.getMoneyAmount()).isEqualTo(44.45);
        assertThat(result.getEmail()).isEqualTo("test@Mock.com");
    }

    @Test
    public void addingProperlyABankAccount() {
        //ARRANGE
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountIban("555444111");
        bankAccount.setAccountName("bank");
        //ACT
        moneyTransferService.addABankAccount(bankAccount);
        //ASSERT
        verify(bankAccountRepository, times(1)).save(acBankAccount.capture());
        assertThat(acBankAccount.getValue().getAccountIban()).isEqualTo(bankAccount.getAccountIban());
        assertThat(acBankAccount.getValue().getAccountName()).isEqualTo(bankAccount.getAccountName());
        assertThat(acBankAccount.getValue().getEmail()).isEqualTo(userAccount.getEmail());
    }


}