package com.ncedu.network24.networkapp.service;

import com.ncedu.network24.networkapp.domain.Chat;
import com.ncedu.network24.networkapp.repositories.ChatRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepo chatRepo;

    public Chat findChat(Long receiverId, Long senderId) {
        Chat chat;
        if (chatRepo.findChatByFirstUserIdAndSecondUserId(receiverId, senderId) != null) {
            chat = chatRepo.findChatByFirstUserIdAndSecondUserId(receiverId, senderId);
        } else if (chatRepo.findChatByFirstUserIdAndSecondUserId(senderId, receiverId) != null) {
            chat = chatRepo.findChatByFirstUserIdAndSecondUserId(senderId, receiverId);
        } else {
            Chat newChat = new Chat();
            newChat.setFirstUserId(receiverId);
            newChat.setSecondUserId(senderId);
            try {
                chatRepo.save(newChat);
            } catch (DataIntegrityViolationException e) {
            }
            chat = newChat;
        }
        return chat;
    }
}
