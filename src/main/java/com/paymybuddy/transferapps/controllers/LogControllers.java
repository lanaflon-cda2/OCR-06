package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Logs;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LogControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/")
    public String getLog(Model model) {
        model.addAttribute("logs", new Logs());
        return "LogPage";
    }

    @RequestMapping(value = "/log/submit")
    public String getConnection(Logs logs, Model model) {
        UserAccount userAccount = accountService.getConnection(logs);
        if (userAccount != null) {
            return "redirect:/userHome";
        }
        return "redirect:/";
    }

    @RequestMapping("/userHome")
    public String userPage(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("name", accountService.getAccountInfo().getName());
            return "UserPage";
        }
        return "redirect:/";
    }
}
