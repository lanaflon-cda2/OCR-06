package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DisconnectControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/disconnect")
    public String disconnect() {
            accountService.disconnect();
            return "redirect:/";
    }
}
