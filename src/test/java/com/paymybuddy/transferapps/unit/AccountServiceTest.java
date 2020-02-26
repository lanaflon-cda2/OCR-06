package com.paymybuddy.transferapps.unit;

import com.paymybuddy.transferapps.dao.AccountDAO;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.service.AccountService;
import com.paymybuddy.transferapps.util.InputReaderUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AccountServiceTest {


    @Mock
    private AccountDAO accountDAO;
    @Mock
    private InputReaderUtil inputReaderUtil;

    private UserAccount userAccount;
    private List<String> bankAccounts;
    private List<String> relatives;

    @InjectMocks
    AccountService accountService = new AccountService();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userAccount = new UserAccount();
        userAccount.setMoneyAmount(100);
        userAccount.setEmail("test@Mock.com");
        userAccount.setName("John");
        accountService.userAccount = userAccount;
        relatives = new ArrayList<>();
        relatives.add("r1");
        relatives.add("r2");
        bankAccounts = new ArrayList<>();
        bankAccounts.add("b1");
        bankAccounts.add("b2");
    }

    @Test
    public void returnTrueAfterEnterGoodLogs() {
        //ARRANGE
        when(inputReaderUtil.readString(anyString())).thenReturn("test@Mock.com").thenReturn("John");
        when(accountDAO.userConnected(anyString(), anyString())).thenReturn(true);
        when(accountDAO.getUserInfo(anyString())).thenReturn(userAccount);
        //ACT
        Boolean result = accountService.getConnection();
        //ASSERT
        assertThat(result).isTrue();
    }

    @Test
    public void returnTrueAfterAddBuddy() {
        //ARRANGE
        String email = "test2@Mock.com";
        when(inputReaderUtil.readString(anyString())).thenReturn(email);
        when(accountDAO.addRelativeConnection(anyString(), anyString())).thenReturn(true);

        //ACT
        Boolean result = accountService.getConnection();

        //ASSERT
        assertThat(result).isTrue();
    }

    @Test
    public void returnGoodAmountOfMoneyDuringWithdrawFromBank() {
        //ARRANGE
        when(inputReaderUtil.readDouble(anyString())).thenReturn(55.55);
        doNothing().when(accountDAO).updateMoneyOnAccount(anyString(), 55.55);
        when(accountDAO.getUserInfo(anyString())).thenReturn(userAccount);
        ArgumentCaptor amount = ArgumentCaptor.forClass(Double.class);

        //ACT
        accountService.withDrawMoneyFromBankAndAddOnTheAccount();

        //ASSERT
        //verify(userAccount, times(1)).setMoneyAmount((Double) amount.capture());
        assertThat(amount).isEqualTo(55.55);
    }

    @Test
    public void returnGoodAmountOfMoneyDuringSendingToFriend() {
        //ARRANGE
        //choose the relative:
        when(inputReaderUtil.readInt(anyString())).thenReturn(1);
        //choose the amount:
        when(inputReaderUtil.readDouble(anyString())).thenReturn(55.55);
        //add a comment:
        when(inputReaderUtil.readString(anyString())).thenReturn("This is a transaction");

        when(accountDAO.getRelatives(anyString())).thenReturn(relatives);
        doNothing().when(accountDAO).updateMoneyOnAccount(anyString(), eq(55.55));
        doNothing().when(accountDAO).addTransaction(anyString(), anyString(), anyDouble(), anyDouble(), anyString());

        ArgumentCaptor amount = ArgumentCaptor.forClass(Double.class);

        //ACT
        accountService.sendMoneyToARelative();

        //ASSERT
        verify(accountDAO, times(2)).updateMoneyOnAccount(anyString(), anyDouble());
        //verify(accountDAO, times(1)).addTransaction(anyString(), anyString(), anyDouble(), anyDouble(), anyString());
        verify(accountDAO, times(1)).getUserInfo(anyString());
    }

    @Test
    public void returnGoodAmountOfMoneyDuringSendingToBankAccount() {
        //ARRANGE
        //choose the bank:
        when(inputReaderUtil.readInt(anyString())).thenReturn(0);
        //choose the amount:
        when(inputReaderUtil.readDouble(anyString())).thenReturn(55.55);
        //add a comment:
        when(inputReaderUtil.readString(anyString())).thenReturn("This is a transaction");
        when(accountDAO.getRelatives(anyString())).thenReturn(relatives);
        when(accountDAO.getBankAccounts(anyString())).thenReturn(bankAccounts);
        when(accountDAO.getUserInfo(anyString())).thenReturn(userAccount);
        doNothing().when(accountDAO).addTransaction(anyString(), anyString(), anyDouble(), anyDouble(), anyString());

        //ACT
        accountService.sendMoneyToARelative();

        //ASSERT
        verify(accountDAO, times(2)).updateMoneyOnAccount(anyString(), anyDouble());
        verify(accountDAO, times(5)).getBankAccounts(anyString());
        //verify(accountDAO, times(1)).addTransaction(anyString(), anyString(), anyDouble(), anyDouble(), anyString());
        verify(accountDAO, times(1)).getUserInfo(anyString());
    }
}