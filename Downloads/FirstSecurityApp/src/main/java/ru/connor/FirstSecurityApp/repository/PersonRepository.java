package ru.connor.FirstSecurityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.FirstSecurityApp.model.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
}
