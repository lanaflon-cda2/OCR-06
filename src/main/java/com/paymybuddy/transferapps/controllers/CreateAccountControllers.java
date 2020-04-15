package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.CreateAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * A non-authetificated guest can just either logging, or create an account with email, name and password
 */

@Controller
public class CreateAccountControllers {

    @Autowired
    private ConnectionService connectionService;

    @GetMapping(value = "/account/create")
    public String createAccount(Model model) {
        model.addAttribute("createAccount", new CreateAccount());
        return "CreateAccount";
    }

    @PostMapping(value = "/account/creating")
    public String creatingAccount(@Valid CreateAccount createAccount, BindingResult result) {
        if (!result.hasErrors()) {
            connectionService.createAnAccount(createAccount);
            return "redirect:/";
        }
        return "redirect:/account/create";
    }
}
