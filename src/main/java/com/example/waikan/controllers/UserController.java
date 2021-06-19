package com.example.waikan.controllers;

import com.example.waikan.models.User;
import com.example.waikan.services.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/profile")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(@AuthenticationPrincipal User user,
                          Model model) {
        User updateUser = userService.getUpdateUserFromDb(user);
        model.addAttribute("user", updateUser);
        model.addAttribute("avatar", updateUser.getAvatar());
        return "profile";
    }

    @PostMapping("/edit")
    public String editProfile(
            @RequestParam("email") String email,
            @RequestParam("nikName") String nikName,
            @RequestParam("phoneNumber") String phoneNumber,
            @RequestParam("file") MultipartFile avatar) throws IOException {
        userService.editProfile(nikName, phoneNumber, avatar, email);
        return "redirect:/profile";
    }
}
