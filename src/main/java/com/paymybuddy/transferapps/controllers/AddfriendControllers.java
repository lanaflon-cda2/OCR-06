package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.dto.Reader;
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
            model.addAttribute("relative", new RelationEmail());
            return "FriendAdd";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/friend/adding")
    public String addingAFriend(RelationEmail relationEmail) {
        if (accountService.isConnected()) {
            accountService.addAFriend(relationEmail);
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
