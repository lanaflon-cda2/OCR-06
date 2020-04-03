package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LogControllers {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping("/")
    public String getLog(Model model) {
        return "LogPage";
    }

    @GetMapping("/userHome")
    public String userPage(Model model) {
            model.addAttribute("userAccount", connectionService.getAccountInfo());
            return "UserPage";
    }
}
