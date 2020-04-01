package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class SendMoneyControllers {

    @Autowired
    private RelativeService relativeService;
    @Autowired
    private MoneyTransferService moneyTransferService;


    @RequestMapping(value = "/userHome/sendMoney/send", method = RequestMethod.GET)
    public String sendMoney(Model model) {
        model.addAttribute("sendMoney", new SendMoney());
        model.addAttribute("relativesEmail", relativeService.getRelatives());
        return "sendMoney";
    }

    @RequestMapping(value = "/userHome/sendMoney/sending",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String sending(@RequestBody SendMoney sendMoney) {
        moneyTransferService.sendMoneyToARelative(sendMoney);
        return "redirect:/userHome";
    }
}
