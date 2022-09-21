package ru.connor.FirstSecurityApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.connor.FirstSecurityApp.model.Person;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/register")
    public String registration(@ModelAttribute("person") Person person){
        return "auth/registration";
    }
}
