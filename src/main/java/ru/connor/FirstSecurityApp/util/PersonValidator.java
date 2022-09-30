package ru.connor.FirstSecurityApp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.connor.FirstSecurityApp.model.Person;
import ru.connor.FirstSecurityApp.services.PersonDetailsService;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Person person = (Person) o;

        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        }catch (UsernameNotFoundException usernameNotFoundException){
            return;
        }

        errors.rejectValue("username", "", "Пользователь с таким ФИО уже существует ");
    }
}










