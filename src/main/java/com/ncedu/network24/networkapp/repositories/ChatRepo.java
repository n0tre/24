package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRepo extends CrudRepository<Chat, Long> {
    List<Chat> findChatsByFirstUserId(Long firstUserId);

    List<Chat> findChatsBySecondUserId(Long secondUserId);

    Chat findChatByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId);
}
