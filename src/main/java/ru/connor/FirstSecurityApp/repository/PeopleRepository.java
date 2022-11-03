package ru.connor.FirstSecurityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.FirstSecurityApp.model.Administration;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Administration, Integer> {
    Optional<Administration> findByUsername(String username);
}