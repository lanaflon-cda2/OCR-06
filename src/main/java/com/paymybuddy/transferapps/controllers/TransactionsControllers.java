package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TransactionsControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/transactionInfo")
    public String getTransactionInfo(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("transactions", accountService.getTransactionInfo());
            return "TransactionInfo";
        }
        return "redirect:/";
    }
}
