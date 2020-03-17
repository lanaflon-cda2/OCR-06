package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TransactionsControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/userHome/transactionInfo")
    public String getTransactionInfo(Model model) {
            model.addAttribute("transactions", moneyTransferService.getTransactionInfo());
            return "TransactionInfo";
    }
}
