package ru.connor.FirstSecurityApp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.connor.FirstSecurityApp.model.Administration;
import ru.connor.FirstSecurityApp.repository.PeopleRepository;
import ru.connor.FirstSecurityApp.security.PersonDetails;
import ru.connor.FirstSecurityApp.services.RegistrationService;
import ru.connor.FirstSecurityApp.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PeopleRepository adminRepository ;


    @GetMapping("/login")
    public String loginPage(){
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("administrator") Administration administration){
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("administrator") @Valid Administration administration, BindingResult bindingResult){
        personValidator.validate(administration, bindingResult);

        if (bindingResult.hasErrors()){
            return "/auth/registration";
        }
        registrationService.register(administration);

        return "auth/successReg";
    }

}

