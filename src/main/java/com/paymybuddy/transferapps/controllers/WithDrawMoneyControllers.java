package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.Reader;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class WithDrawMoneyControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/withdrawMoney/withdraw")
    public String withdrawMoney(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("withdrawMoney", new Reader());
            model.addAttribute("bankAccounts", accountService.getBankAccounts());
            return "withdrawMoney";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/withdrawMoney/withdrawing")
    public String withdrawing(Reader reader) {
        if (accountService.isConnected()) {
            accountService.withDrawMoneyFromBankAndAddOnTheAccount(reader.getStringReader(), reader.getDoubleReader());
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
