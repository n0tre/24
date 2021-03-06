package com.ncedu.network24.networkapp.service;

import com.ncedu.network24.networkapp.domain.Chat;
import com.ncedu.network24.networkapp.domain.ChatMessage;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.repositories.ChatMessageRepo;
import com.ncedu.network24.networkapp.repositories.ChatRepo;
import com.ncedu.network24.networkapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ChatMessageRepo chatMessageRepo;

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
            Long[] array = new Long[]{receiverId, senderId};
            Arrays.sort(array);
            String a = array[0].toString();
            String b = array[1].toString();
            String concatenate = a + "_" + b;
            newChat.setChatId(concatenate);
            try {
                chatRepo.save(newChat);
            } catch (DataIntegrityViolationException exception) {
                System.out.println("Data Integrity Exception!");
                if (chatRepo.findChatByFirstUserIdAndSecondUserId(receiverId, senderId) != null) {
                    newChat = chatRepo.findChatByFirstUserIdAndSecondUserId(receiverId, senderId);
                } else if (chatRepo.findChatByFirstUserIdAndSecondUserId(senderId, receiverId) != null) {
                    newChat = chatRepo.findChatByFirstUserIdAndSecondUserId(senderId, receiverId);
                }
            }
            chat = newChat;
        }
        return chat;
    }

    public void save(ChatMessage chatMessage) {
        chatMessageRepo.save(chatMessage);
    }

    public List<User> getReceivers(User user) {
        List<User> receivers = userRepo.findAll();
        return receivers;
    }

    public List<ChatMessage> getChatMessages(String chatId) {
        List<ChatMessage> messages = chatMessageRepo.getChatMessagesByChat(chatId);
        return messages;
    }


}
