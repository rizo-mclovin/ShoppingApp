package com.example.waikan.controllers;

import com.example.waikan.models.User;
import com.example.waikan.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public String registerNewUser(User user, Model model) {
        boolean registrationWasSuccessful = userService.createUser(user);
        if (!registrationWasSuccessful) {
            model.addAttribute("user", user);
            model.addAttribute("errorRegistration", "Пользователь уже существует");
            return "registration";
        } else return "redirect:/login";
    }

    @GetMapping
    public String registration() { return "registration"; }
}
