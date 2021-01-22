package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.ChatMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ChatMessageRepo extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> getChatMessagesByChat(String chat);
}
