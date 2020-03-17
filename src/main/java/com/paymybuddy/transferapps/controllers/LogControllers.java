package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LogControllers {

    @Autowired
    private ConnectionService connectionService;

    @RequestMapping("/")
    public String getLog(Model model) {
        model.addAttribute("logs", new Logs());
        return "LogPage";
    }

    @RequestMapping(value = "/log/submit")
    public String getConnection(Logs logs) {
        UserAccount userAccount = connectionService.getConnection(logs);
        if (userAccount != null) {
            return "redirect:/userHome";
        }
        return "redirect:/";
    }

    @RequestMapping("/userHome")
    public String userPage(Model model) {
            model.addAttribute("userAccount", connectionService.getAccountInfo());
            return "UserPage";
    }
}
