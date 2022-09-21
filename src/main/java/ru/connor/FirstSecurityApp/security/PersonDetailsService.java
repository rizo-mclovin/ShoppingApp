package ru.connor.FirstSecurityApp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.connor.FirstSecurityApp.model.Person;
import ru.connor.FirstSecurityApp.repository.PersonDetailsRepository;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonDetailsRepository personDetailsRepository;

    @Autowired
    public PersonDetailsService(PersonDetailsRepository personDetailsRepository) {
        this.personDetailsRepository = personDetailsRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personDetailsRepository.findByName(username);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return new PersonDetails(person.get());
    }
}
