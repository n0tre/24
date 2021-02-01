package com.ncedu.network24.networkapp.service;

import com.ncedu.network24.networkapp.domain.Post;
import com.ncedu.network24.networkapp.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepo postRepo;

    public Iterable<Post> findByTag(String filter) {
        return postRepo.findByTag(filter);
    }

    public Iterable<Post> findAll() {
        return postRepo.findAll();
    }

    public void savePost(Post post) {
        postRepo.save(post);
    }
}
