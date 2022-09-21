package ru.connor.FirstSecurityApp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.connor.FirstSecurityApp.model.Person;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }
}
