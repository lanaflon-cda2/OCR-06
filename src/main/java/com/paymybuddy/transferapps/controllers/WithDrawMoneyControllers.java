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
public class WithDrawMoneyControllers {

    @Autowired
    private MoneyTransferService moneyTransferService;

    @RequestMapping(value = "/userHome/withdrawMoney/withdraw", method = RequestMethod.GET)
    public String withdrawMoney(Model model) {
        model.addAttribute("withdrawMoney", new Deposit());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "withdrawMoney";
    }

    @RequestMapping(value = "/userHome/withdrawMoney/withdrawing",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String withdrawing(@RequestBody Deposit deposit) {
        moneyTransferService.withDrawMoneyFromBankAndAddOnTheAccount(deposit);
        return "redirect:/userHome";
    }
}
