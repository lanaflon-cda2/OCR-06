package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * A friend or relative is a user of the application whose the current user added to his friendlist
 * Only friends/relatives can send money to each other
 */

@Controller
public class AddfriendControllers {

    @Autowired
    private RelativeService relativeService;

    @GetMapping(value = "/userHome/friend/add")
    public String addAFriendToYourList(Model model) {
            model.addAttribute("relative", new RelationEmail());
            return "FriendAdd";
    }

    @PostMapping(value = "/userHome/friend/adding")
    public String addingAFriend( RelationEmail relationEmail) {
            if (!relativeService.addAFriend(relationEmail)) {
                return "redirect:/userHome/friend/add";
            }
            return "redirect:/userHome";
    }
}
