package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Chat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ChatRepo extends CrudRepository<Chat, Long> {

    Chat findChatByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId);


    Set<Chat> findChatByFirstUserId(Long userId);

    Set<Chat> findChatBySecondUserId(Long userId);

    List<Chat> findAllByFirstUserId(Long userId);

    List<Chat> findAllBySecondUserId(Long userId);

}
