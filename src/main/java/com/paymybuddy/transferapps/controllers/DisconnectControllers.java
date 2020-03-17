package com.paymybuddy.transferapps.controllers;

import com.paymybuddy.transferapps.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class DisconnectControllers {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping(value = "/userHome/disconnect")
    public String disconnect() {
        connectionService.disconnect();
        return "redirect:/";
    }
}
