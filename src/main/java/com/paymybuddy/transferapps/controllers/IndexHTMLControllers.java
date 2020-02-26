package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.Logs;
import com.paymybuddy.transferapps.domain.UserAccount;
import com.paymybuddy.transferapps.service.AccountService;
import com.paymybuddy.transferapps.service.InteractiveShell;
import com.paymybuddy.transferapps.util.TimeConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;


@Controller
public class IndexHTMLControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/")
    public String getLog(Model model) {
        List<String> logs = new ArrayList<>();
        model.addAttribute("logs", new Logs());
        return "LogPage";
    }

    @RequestMapping(value = "/log/submit")
    public String getConnection(Logs logs) {
        UserAccount userAccount = accountService.getConnection(logs.getEmail(), logs.getPassword());
        if (!username.equals("/")){
            return "redirect:/{username}/";
        }
        return "redirect:/";
    }

    @RequestMapping("/{username}/")
    public String userPage(@PathVariable("username") String username, Model model) {
        if(accountService.userAccount.getTimeConnection().isActive())
        return "UserPage";
    }

    @RequestMapping(value = "/station/setting")
    public String updatingfirestation(Firestation firestation) {
        firestationService.setFirestation(firestation.getAddress(), firestation.getStation());
        return "redirect:/";
    }

    @RequestMapping(value = "/station/del/{address}")
    public String deletingfirestation(@PathVariable("address") String address) {
        firestationService.deleteFirestation(address);
        return "redirect:/";
    }

    @RequestMapping("/station/info/{address}")
    public String infoFirestation(@PathVariable("address") String address, Model model) {
        model.addAttribute("firestation", firestationService.findFirestationByAddress(address));
        return "firestation";
    }
}
