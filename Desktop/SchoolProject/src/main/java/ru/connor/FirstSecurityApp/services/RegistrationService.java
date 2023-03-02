package ru.connor.FirstSecurityApp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.connor.FirstSecurityApp.model.Administration;
import ru.connor.FirstSecurityApp.repository.PeopleRepository;


@Service
@RequiredArgsConstructor
public class RegistrationService{
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(Administration administration){
        administration.setPassword(passwordEncoder.encode(administration.getPassword()));
//        administration.setRole("ROLE_USER");
        peopleRepository.save(administration);
    }

}
