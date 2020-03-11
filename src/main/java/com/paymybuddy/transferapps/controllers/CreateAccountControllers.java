package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CreateAccountControllers {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(value = "/account/create")
    public String createAccount(Model model) {
        model.addAttribute("createAccount", new CreateAccount());
        return "CreateAccount";
    }

    @RequestMapping(value = "/account/creating")
    public String createAccount(CreateAccount createAccount) {
        connectionService.createAnAccount(createAccount);
        return "redirect:/";
    }
}
