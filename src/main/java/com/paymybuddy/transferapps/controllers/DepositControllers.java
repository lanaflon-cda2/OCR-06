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
 * Deposist is the action of sending the money which is on the account to the selected bank belonging to the user
 */

@Controller
public class DepositControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @GetMapping(value = "/userHome/depositMoney/deposit")
    public String depositMoney(Model model) {
        model.addAttribute("depositMoney", new Deposit());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "depositMoney";
    }

    @PostMapping(value = "/userHome/depositMoney/depositing")
    public String depositing( Deposit deposit) {
        if(moneyTransferService.depositMoneyToBankAccount(deposit)) {
            return "redirect:/userHome";
        }
        return "redirect:/userHome/depositMoney/deposit";
    }
}
