package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.Post;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;


@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${FILE_FORMATS}")
    private String fileFormats;

    @GetMapping("/")
    public String greeting(String name, Map<String, Object> model) {
        return "greeting";
    }

    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Post> page;
        if (filter != null && !filter.isEmpty()) {
            page = postService.findByTag(filter, pageable);
        } else {
            page = postService.findAll(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Post post,
            BindingResult bindingResult,
            Model model,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("file") MultipartFile file
            )
            throws IOException
    {
        post.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("post", post);

        } else {
            if (file != null) {
                if (!(file.getContentType().equalsIgnoreCase(fileFormats)) & (!file.getOriginalFilename().isEmpty())) {
                    File uploadDir = new File(uploadPath);
                    if (uploadDir.exists()) {
                        uploadDir.mkdir();
                    }

                    String uuidFile = UUID.randomUUID().toString();
                    String resultFilename = uuidFile + "." + file.getOriginalFilename();

                    file.transferTo(new File(uploadPath + "/" + resultFilename));
                    post.setFilename(resultFilename);


                }
            }
        }
        model.addAttribute("post", null);
        postService.savePost(post);

       Page<Post> page = postService.findAll(pageable);
       model.addAttribute("page", page);

        return "main";
    }


    @RequestMapping(value = "/accessDenied")
    public String accessDenied() {
        return "accessDenied";
    }

}