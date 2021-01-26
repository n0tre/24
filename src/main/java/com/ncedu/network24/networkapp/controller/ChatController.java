package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.Chat;
import com.ncedu.network24.networkapp.domain.ChatMessage;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.repositories.ChatMessageRepo;
import com.ncedu.network24.networkapp.repositories.ChatRepo;
import com.ncedu.network24.networkapp.repositories.UserRepo;
import com.ncedu.network24.networkapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class ChatController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ChatService chatService;

    @MessageMapping("/chatRegister")
    public void register(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getChat() + "/messages", chatMessage);
    }

    @MessageMapping("/chatSend")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        chatMessageRepo.save(chatMessage);
        messagingTemplate.convertAndSend("/topic/" + chatMessage.getChat() + "/messages", chatMessage);
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/privateChat")
    public String showPrivatePage(@AuthenticationPrincipal User user, Model model) {
       List<User> receivers = chatRepo.listOfChats(user.getId(), user.getId());
       model.addAttribute("sender", user);
       model.addAttribute("receivers", receivers);
        return "privateChat";
    }

    @PostMapping(value = "/privateChat")
    public ResponseEntity<Chat> privatePage(@AuthenticationPrincipal User user, HttpServletRequest request) {
        Long receiverId = Long.parseLong(request.getParameter("receiverId"));
        Long senderId = user.getId();
        Chat chat = chatService.findChat(receiverId, senderId);
        return ResponseEntity.ok(chat);
    }

    @PostMapping(value = "/privateChatMessages")
    public ResponseEntity<List<ChatMessage>> getPrivateChatMessages(HttpServletRequest request) {
        String chatId = request.getParameter("chatId");
        List<ChatMessage> messages = chatMessageRepo.getChatMessagesByChat(chatId);
        return ResponseEntity.ok(messages);
    }
}
