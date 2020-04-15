package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Withdrawing is the action of retrieving the money which is on a bank account to the current user account of the application
 */

@Controller
public class WithDrawMoneyControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @GetMapping(value = "/userHome/withdrawMoney/withdraw")
    public String withdrawMoney(Model model) {
        model.addAttribute("withdrawMoney", new Deposit());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "withdrawMoney";
    }

    @PostMapping(value = "/userHome/withdrawMoney/withdrawing")
    public String withdrawing(Deposit deposit) {
        if(moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(deposit)) {
            return "redirect:/userHome";
        }
        return "redirect:/userHome/withdrawMoney/withdraw";
    }
}
