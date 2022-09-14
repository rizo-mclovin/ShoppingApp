package ru.connor.FirstSecurityApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.connor.FirstSecurityApp.service.PersonService;

import java.util.Collections;

@Component
public class AuthProvider implements AuthenticationProvider {

    private final PersonService personService;

    @Autowired
    public AuthProvider(PersonService personService) {
        this.personService = personService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails userDetails = personService.loadUserByUsername(username);

        String password = authentication.getCredentials().toString();
        if (!password.equals(userDetails.getPassword())){
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
