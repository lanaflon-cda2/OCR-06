package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CreateAccountControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/account/create")
    public String createAccount(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("createAccount", new CreateAccount());
            return "CreateAccount";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/account/create")
    public String createAccount(CreateAccount createAccount) {
        if (accountService.isConnected()) {
            if (createAccount.getPassword()==createAccount.getConfirmPassword()) {
                accountService.createAnAccount(createAccount);
                return "redirect:/userHome";
            }
        }
        return "redirect:/";
    }
}
