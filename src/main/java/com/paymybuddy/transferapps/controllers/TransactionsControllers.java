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
    private ConnectionService connectionService;
    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/transactionInfo")
    public String getTransactionInfo(Model model) {
        if (connectionService.isConnected()) {
            model.addAttribute("transactions", moneyTransferService.getTransactionInfo());
            return "TransactionInfo";
        }
        return "redirect:/";
    }
}
