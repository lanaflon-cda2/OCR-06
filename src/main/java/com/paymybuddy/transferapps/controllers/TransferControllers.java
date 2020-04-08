package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.SendMoney;
import com.paymybuddy.transferapps.service.MoneyTransferService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class TransferControllers {

    @Autowired
    private RelativeService relativeService;
    @Autowired
    private MoneyTransferService moneyTransferService;


    @GetMapping(value = "/userHome/transfer")
    public String sendMoney(Model model) {
        model.addAttribute("sendMoney", new SendMoney());
        model.addAttribute("relativesEmail", relativeService.getRelatives());
        model.addAttribute("transactions", moneyTransferService.getTransactionInfo());
        return "Transfer";
    }

    @PostMapping(value = "/userHome/sendMoney/sending")
    public String sending(SendMoney sendMoney) {
        moneyTransferService.sendMoneyToARelative(sendMoney);
        return "redirect:/transfer";
    }
}
