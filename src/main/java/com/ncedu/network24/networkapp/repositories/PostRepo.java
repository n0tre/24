package com.ncedu.network24.networkapp.repositories;

import com.ncedu.network24.networkapp.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepo extends CrudRepository<Post, Long> {
    Page<Post> findByTag(String tag, Pageable pageable);

    Page<Post> findAll(Pageable pageable);
}
