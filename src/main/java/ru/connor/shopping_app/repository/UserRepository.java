package ru.connor.shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.connor.shopping_app.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findAllByEmail(String email);

    User findByEmail(String email);
}
