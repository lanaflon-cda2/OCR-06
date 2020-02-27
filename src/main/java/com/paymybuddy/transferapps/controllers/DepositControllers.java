package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.Reader;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DepositControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/depositMoney/deposit")
    public String depositMoney(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("depositMoney", new Reader());
            model.addAttribute("bankAccounts", accountService.getBankAccounts());
            return "depositMoney";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/depositMoney/depositing")
    public String depositing(Reader reader) {
        if (accountService.isConnected()) {
            accountService.depositMoneyToBankAccount(reader.getStringReader(), reader.getDoubleReader());
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
