package com.paymybuddy.transferapps.unit.controller;

import com.paymybuddy.transferapps.controllers.AddfriendControllers;
import com.paymybuddy.transferapps.domain.RelationEmail;
import com.paymybuddy.transferapps.service.RelativeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Timestamp;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class AddFriendControllerTest {

    @Mock
    private RelativeService relativeService;
    @Mock
    private Model model;

    @InjectMocks
    AddfriendControllers addfriendControllers = new AddfriendControllers();

    @Test
    public void fillFriendFormWithSuccess() {

        String result = addfriendControllers.addAFriendToYourList(model);
        verify(model, (times(1))).addAttribute(eq("relative"), any());

        assertThat(result).isEqualTo("FriendAdd");
    }

    @Test
    public void postNewFriendWithSuccess() {
        when(relativeService.addAFriend(any())).thenReturn(true);

        RelationEmail relationEmail = new RelationEmail();
        relationEmail.setRelativeEmail("friend@test.com");
        String result = addfriendControllers.addingAFriend(relationEmail);

        assertThat(result).isEqualTo("redirect:/userHome");
    }
}
