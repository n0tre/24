package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.model.ChatMessage;
import com.ncedu.network24.networkapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private UserRepo userRepo;

   @MessageMapping("/chat.register")
   @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
       return chatMessage;
    }


    @GetMapping("/privateChat")
    public String privatePage(@AuthenticationPrincipal User user, Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("user", user);
        model.addAttribute("users", users);
        return "privateChat";
    }

}
