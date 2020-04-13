package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.controllers.AddBankAccountControllers;
import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)

public class AddBankAccountControllerTest {

    @Mock
    private MoneyTransferService moneyTransferService;
    @Mock
    private Model model;

    @InjectMocks
    AddBankAccountControllers addBankAccountControllers = new AddBankAccountControllers();

    @Test
    public void fillFriendFormWithSuccess() {

        String result = addBankAccountControllers.addABankAccountToYourList(model);
        verify(model, (times(1))).addAttribute(eq("bankAccount"), any());

        assertThat(result).isEqualTo("bankAccountAdd");
    }
    @Test
    public void addingBankAccountController() {
        when(moneyTransferService.addABankAccount(any())).thenReturn(true);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountName("account1");
        bankAccount.setAccountIban("555444888");
        String result = addBankAccountControllers.addingABankAccount(bankAccount);

        assertThat(result).isEqualTo("redirect:/userHome");
    }
}