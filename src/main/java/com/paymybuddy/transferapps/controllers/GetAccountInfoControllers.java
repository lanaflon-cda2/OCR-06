package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GetAccountInfoControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/accountInfo")
    public String getAccountInfo(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("userAccount", accountService.getAccountInfo());
            model.addAttribute("relatives", accountService.getRelatives());
            model.addAttribute("bankAccounts", accountService.getBankAccounts());
            return "AccountInfo";
        }
        return "redirect:/";
    }
}
