package ru.connor.shopping_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.connor.shopping_app.model.User;
import ru.connor.shopping_app.repository.UserRepository;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findAllByEmail(s);
        if (user.isEmpty()) throw new UsernameNotFoundException("User not found");

        return new User(user.get());
    }
}