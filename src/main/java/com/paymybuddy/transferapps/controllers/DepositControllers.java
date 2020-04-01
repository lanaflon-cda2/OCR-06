package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class DepositControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/userHome/depositMoney/deposit", method = RequestMethod.GET)
    public String depositMoney(Model model) {
        model.addAttribute("depositMoney", new Deposit());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "depositMoney";
    }

    @RequestMapping(value = "/userHome/depositMoney/depositing",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String depositing(@RequestBody Deposit deposit) {
        moneyTransferService.depositMoneyToBankAccount(deposit);
        return "redirect:/userHome";
    }
}
