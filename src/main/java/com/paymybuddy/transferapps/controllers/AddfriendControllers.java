package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.Reader;
import com.paymybuddy.transferapps.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AddfriendControllers {

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/friend/add")
    public String addAFriendToYourList(Model model) {
        if (accountService.isConnected()) {
            model.addAttribute("relative", new Reader());
            return "FriendAdd";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/friend/adding")
    public String addingAFriend(Reader reader) {
        if (accountService.isConnected()) {
            accountService.addAFriend(reader.getStringReader());
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
