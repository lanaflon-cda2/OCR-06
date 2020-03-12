package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class AddfriendControllers {

    @Autowired
    private ConnectionService connectionService;
    @Autowired
    private RelativeService relativeService;

    @RequestMapping(value = "/friend/add")
    public String addAFriendToYourList(Model model) {
        if (connectionService.isConnected()) {
            model.addAttribute("relative", new RelationEmail());
            return "FriendAdd";
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/friend/adding")
    public String addingAFriend(RelationEmail relationEmail) {
        if (connectionService.isConnected()) {
            if (relativeService.addAFriend(relationEmail) == false) {
                return "redirect:/friend/add";
            }
            return "redirect:/userHome";
        }
        return "redirect:/";
    }
}
