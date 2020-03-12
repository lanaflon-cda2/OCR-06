package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DepositControllers {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/depositMoney/deposit")
    public String depositMoney(Model model) {
        if (connectionService.isConnected()) {
            model.addAttribute("depositMoney", new Deposit());
            model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
            return "depositMoney";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/depositMoney/depositing")
    public String depositing(Deposit deposit) {
        if (connectionService.isConnected()) {
            moneyTransferService.depositMoneyToBankAccount(deposit);
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
