package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.Post;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.repositories.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller

public class PostController {
    @Autowired
    private PostRepo postRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String greeting(String name, Map<String, Object> model) {
        return "greeting";
    }

    @PreAuthorize("hasAnyAuthority('USER')")
    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model) {
        Iterable<Post> messages = postRepo.findAll();

        if (filter != null && !filter.isEmpty()) {
            messages = postRepo.findByTag(filter);
        } else {
            messages = postRepo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model,
            @RequestParam("file") MultipartFile file) throws IOException {
        Post post = new Post(text, tag, user);

        if (file != null & !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            post.setFilename(resultFilename);

        }
        postRepo.save(post);

        Iterable<Post> messages = postRepo.findAll();
        model.put("messages", messages);

        return "main";
    }
}