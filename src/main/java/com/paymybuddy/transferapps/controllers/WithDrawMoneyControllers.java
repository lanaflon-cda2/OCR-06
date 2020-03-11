package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Deposit;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WithDrawMoneyControllers {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/withdrawMoney/withdraw")
    public String withdrawMoney(Model model) {
        if (connectionService.isConnected()) {
            model.addAttribute("withdrawMoney", new Deposit());
            model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
            return "withdrawMoney";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/withdrawMoney/withdrawing")
    public String withdrawing(Deposit deposit) {
        if (connectionService.isConnected()) {
            moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(deposit);
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
