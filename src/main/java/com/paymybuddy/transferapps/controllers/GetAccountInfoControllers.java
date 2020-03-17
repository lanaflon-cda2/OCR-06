package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GetAccountInfoControllers {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private MoneyTransferService moneyTransferService;
    @Autowired
    private RelativeService relativeService;

    @RequestMapping(value = "/userHome/accountInfo")
    public String getAccountInfo(Model model) {
        model.addAttribute("userAccount", connectionService.getAccountInfo());
        model.addAttribute("relatives", relativeService.getRelatives());
        model.addAttribute("bankAccounts", moneyTransferService.getBankAccounts());
        return "AccountInfo";
    }
}
