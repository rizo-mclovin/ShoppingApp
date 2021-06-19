package com.example.waikan.controllers;

import com.example.waikan.models.User;
import com.example.waikan.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/registration")
    public String registerNewUser(User user, Model model) {
        boolean registrationWasSuccessful = userService.createUser(user);
        if (!registrationWasSuccessful) {
            model.addAttribute("user", user);
            model.addAttribute("errorRegistration", "Пользователь уже существует");
            return "registration";
        } else {
            model.addAttribute("nikName", user.getNikName());
            model.addAttribute("email", user.getEmail());
            return "register-succssesfully";
        }
    }

    @GetMapping("/activate/{code}")
    public String activateUser(Model model, @PathVariable("code") String code) {
        if (userService.activateUser(code)) {
            model.addAttribute("activate", true);
        } else {
            model.addAttribute("activate", false);
        }
        return "activate";
    }

    @GetMapping("/registration")
    public String registration() { return "registration"; }
}
