package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.Role;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @GetMapping("/user/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/user")
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user) {

        user.setUsername(username);
        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }


        userService.saveUser(user);

        return "redirect:/user";
    }

    @PostMapping("/user/block")
    public String blockUser(@RequestParam("userId") User user) {
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                userService.lockUser(user);
            }
        }
        return "redirect:/user";
    }

    @PostMapping("/user/unblock")
    public String unBlockUser(@RequestParam("userId") User user) {
        if (!user.isAccountNonLocked()) {
            userService.unlockUser(user);
        }
        return "redirect:/user";
    }
}
