package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.dto.Reader;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DepositControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/depositMoney/deposit")
    public String depositMoney(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("depositMoney", new Deposit());
            model.addAttribute("bankAccounts", accountService.getBankAccounts());
            return "depositMoney";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/depositMoney/depositing")
    public String depositing(Deposit deposit) {
        if (accountService.isConnected()) {
            accountService.depositMoneyToBankAccount(deposit);
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
