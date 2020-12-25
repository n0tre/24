package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Long> {
    List<Message> findByTag(String tag);
}
