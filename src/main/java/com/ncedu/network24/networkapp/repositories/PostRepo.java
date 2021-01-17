package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post, Long> {
    List<Post> findByTag(String tag);
}
