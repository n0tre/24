package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Chat;
import com.ncedu.network24.networkapp.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


public interface ChatRepo extends CrudRepository<Chat, Long> {



    Chat findChatByFirstUserIdAndSecondUserId(Long firstUserId, Long secondUserId);


    Set<Chat> findChatByFirstUserId(Long userId);

    Set<Chat> findChatBySecondUserId(Long userId);

    List<Chat> findAllByFirstUserId(Long userId);

    List<Chat> findAllBySecondUserId(Long userId);

    @Query("SELECT DISTINCT u FROM Chat as c INNER JOIN User as u ON c.firstUserId = u.id OR c.secondUserId = u.id")
    @Modifying
    public List<User> listOfChats(Long firstUserId, Long secondUserId);



}
