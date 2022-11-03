package ru.connor.FirstSecurityApp.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.connor.FirstSecurityApp.model.Administration;
import ru.connor.FirstSecurityApp.services.PersonDetailsService;

@Component
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Administration.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Administration administration = (Administration) o;

        try {
            personDetailsService.loadUserByUsername(administration.getUsername());
        }catch (UsernameNotFoundException usernameNotFoundException){
            return;
        }

        errors.rejectValue("username", "", "Пользователь с таким ФИО уже существует ");
    }
}










