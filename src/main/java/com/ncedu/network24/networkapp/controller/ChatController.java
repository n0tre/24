package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.Chat;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.model.ChatMessage;
import com.ncedu.network24.networkapp.repositories.ChatRepo;
import com.ncedu.network24.networkapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class ChatController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.register")
    public void register(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getChat() + "/messages", chatMessage);
    }

    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getChat() + "/messages", chatMessage);
    }


    @GetMapping("/privateChat")
    public String showPrivatePage(@AuthenticationPrincipal User user, Model model) {
        List<User> receivers = userRepo.findAll();
        model.addAttribute("sender", user);
        model.addAttribute("receivers", receivers);
        return "privateChat";
    }

    @PostMapping (value = "/privateChat")
    public ResponseEntity<Chat> privatePage(@AuthenticationPrincipal User user, HttpServletRequest request) {
        Long receiverId = Long.parseLong(request.getParameter("receiverId"));
        Long senderId = user.getId();
        Chat chat;
        if (chatRepo.findChatByFirstUserIdAndSecondUserId(receiverId, senderId) != null) {
            chat = chatRepo.findChatByFirstUserIdAndSecondUserId(receiverId, senderId);
        } else if (chatRepo.findChatByFirstUserIdAndSecondUserId(senderId, receiverId) != null) {
            chat = chatRepo.findChatByFirstUserIdAndSecondUserId(senderId, receiverId);
        } else {
            Chat newChat = new Chat();
            newChat.setFirstUserId(receiverId);
            newChat.setSecondUserId(senderId);
            chatRepo.save(newChat);
            chat = newChat;
        }
        return ResponseEntity.ok(chat);
    }
}
