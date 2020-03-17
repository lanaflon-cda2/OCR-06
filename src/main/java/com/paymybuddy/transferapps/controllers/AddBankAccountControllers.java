package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(params = "onlyForAuthenticated")
public class AddBankAccountControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/userHome/bankAccount/add", method = POST)
    public String addABankAccountToYourList(Model model) {
        model.addAttribute("bankAccount", new BankAccount());
        return "bankAccountAdd";
    }

    @RequestMapping(value = "/userHome/bankAccount/adding", method = POST)
    public String addingABankAccount(BankAccount bankAccount) {
        if (moneyTransferService.addABankAccount(bankAccount) == false) {
            return "redirect:/userHome/bankAccount/add";
        }
        return "redirect:/userHome";
    }
}
