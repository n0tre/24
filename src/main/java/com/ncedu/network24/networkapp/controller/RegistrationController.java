package com.ncedu.network24.networkapp.controller;

import com.ncedu.network24.networkapp.domain.Role;
import com.ncedu.network24.networkapp.domain.User;
import com.ncedu.network24.networkapp.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.dao.*;


import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errors = UtilsController.getErrors(bindingResult);
            model.mergeAttributes(errors);

            return "registration";
        }

        try
          { user.setEnabled(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
            return "redirect:/login";
        }
        catch (DataIntegrityViolationException e) {
            model.addAttribute("usernameError", "User with such username already exists!");
            return "registration";
        }




    }
}
