package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SendMoneyControllers {

    @Autowired
    private RelativeService relativeService;
    @Autowired
    private MoneyTransferService moneyTransferService;


    @RequestMapping(value = "/userHome/sendMoney/send")
    public String sendMoney(Model model) {
        model.addAttribute("sendMoney", new SendMoney());
        model.addAttribute("relativesEmail", relativeService.getRelatives());
        return "sendMoney";
    }

    @RequestMapping(value = "/userHome/sendMoney/sending")
    public String sending(SendMoney sendMoney) {
        moneyTransferService.sendMoneyToARelative(sendMoney);
        return "redirect:/userHome";
    }
}
