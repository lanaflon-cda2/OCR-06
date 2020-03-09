package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.dto.Reader;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class SendMoneyControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/sendMoney/send")
    public String sendMoney(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("sendMoney", new Reader());
            model.addAttribute("relativesEmail", accountService.getRelatives());
            return "sendMoney";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/sendMoney/sending")
    public String sending(Reader reader) {
        if (accountService.isConnected()) {
            accountService.sendMoneyToARelative(reader.getStringReader(), reader.getDoubleReader(), reader.getStringReader2());
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
