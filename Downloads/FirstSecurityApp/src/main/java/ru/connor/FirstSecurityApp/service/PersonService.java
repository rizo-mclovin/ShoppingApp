package ru.connor.FirstSecurityApp.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.connor.FirstSecurityApp.model.Person;
import ru.connor.FirstSecurityApp.repository.PersonRepository;
import ru.connor.FirstSecurityApp.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = personRepository.findByName(username);

        if (person.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }
        return new PersonDetails(person.get());
    }
}
