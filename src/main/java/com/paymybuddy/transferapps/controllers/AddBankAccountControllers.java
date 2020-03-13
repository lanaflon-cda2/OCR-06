package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class AddBankAccountControllers {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/bankAccount/add", method = POST)
    public String addABankAccountToYourList(Model model) {
        if (connectionService.isConnected()) {
            model.addAttribute("bankAccount", new BankAccount());
            return "bankAccountAdd";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/bankAccount/adding", method = POST)
    public String addingABankAccount(BankAccount bankAccount) {
        if (connectionService.isConnected()) {
            if (moneyTransferService.addABankAccount(bankAccount) == false) {
                return "redirect:/bankAccount/add";
            }
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
