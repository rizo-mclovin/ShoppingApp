package ru.connor.FirstSecurityApp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.connor.FirstSecurityApp.model.Administration;
import ru.connor.FirstSecurityApp.repository.PeopleRepository;
import ru.connor.FirstSecurityApp.security.PersonDetails;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Administration> admin = peopleRepository.findByUsername(s);
        if (admin.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(admin.get());
    }
}