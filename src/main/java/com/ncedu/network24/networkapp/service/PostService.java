package com.ncedu.network24.networkapp.service;

import com.ncedu.network24.networkapp.domain.Post;
import com.ncedu.network24.networkapp.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    PostRepo postRepo;

    public Page<Post> findByTag(String filter, Pageable pageable) {
        return postRepo.findByTag(filter, pageable);
    }

    public Page<Post> findAll(Pageable pageable) {
        return postRepo.findAll(pageable);
    }

    public Iterable<Post> findAllPosts() {
        return postRepo.findAll();
    }

    public void savePost(Post post) {
        postRepo.save(post);
    }
}
