package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class WithDrawMoneyControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @GetMapping(value = "/userHome/withdrawMoney/withdraw")
    public String withdrawMoney(Model model) {
        model.addAttribute("withdrawMoney", new Deposit());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "withdrawMoney";
    }

    @PostMapping(value = "/userHome/withdrawMoney/withdrawing")
    public String withdrawing(Deposit deposit) {
        moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(deposit);
        return "redirect:/userHome";

    }
}
