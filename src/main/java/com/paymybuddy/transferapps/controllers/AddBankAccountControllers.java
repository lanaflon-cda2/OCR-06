package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.BankAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@Controller
public class AddBankAccountControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/userHome/bankAccount/add", method = GET)
    public String addABankAccountToYourList(Model model) {
        model.addAttribute("bankAccount", new BankAccount());
        return "bankAccountAdd";
    }

    @RequestMapping(value = "/userHome/bankAccount/adding",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addingABankAccount(@RequestBody BankAccount bankAccount) {
        if (moneyTransferService.addABankAccount(bankAccount) == false) {
            return "redirect:/userHome/bankAccount/add";
        }
        return "redirect:/userHome";
    }
}
