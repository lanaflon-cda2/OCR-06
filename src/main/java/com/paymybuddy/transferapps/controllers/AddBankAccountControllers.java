package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.Reader;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AddBankAccountControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/bankAccount/add")
    public String addABankAccountToYourList(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("bankAccount", new Reader());
            return "bankAccountAdd";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/bankAccount/adding")
    public String addingABank(Reader reader) {
        if (accountService.isConnected()) {
            accountService.addABankAccount(reader.getStringReader());
            return "redirect:/userHome";
        }
        return "redirect:/";
    }

}
