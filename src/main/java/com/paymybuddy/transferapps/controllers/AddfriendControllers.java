package com.paymybuddy.transferapps.controllers;


import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.service.ConnectionService;
import com.paymybuddy.transferapps.service.RelativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class AddfriendControllers {

    @Autowired
    private RelativeService relativeService;

    @RequestMapping(value = "/userHome/friend/add"
            , method = RequestMethod.GET)
    public String addAFriendToYourList(Model model) {
            model.addAttribute("relative", new RelationEmail());
            return "FriendAdd";
    }

    @RequestMapping(value = "/userHome/friend/adding",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addingAFriend(@RequestBody RelationEmail relationEmail) {
            if (!relativeService.addAFriend(relationEmail)) {
                return "redirect:/userHome/friend/add";
            }
            return "redirect:/userHome";
    }
}
