package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
public class CreateAccountControllers {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(value = "/account/create")
    public String createAccount(Model model) {
        model.addAttribute("createAccount", new CreateAccount());
        return "CreateAccount";
    }

    @RequestMapping(value = "/account/creating",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String creatingAccount(@Valid @RequestBody CreateAccount createAccount, BindingResult result) {
        if (!result.hasErrors()) {
            connectionService.createAnAccount(createAccount);
            return "redirect:/";
        }
        return "redirect:/account/create";
    }
}
