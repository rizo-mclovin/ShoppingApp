package ru.connor.FirstSecurityApp.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.FirstSecurityApp.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentsRepository extends JpaRepository<Student, Integer> {
    @Override
    Optional<Student> findById(Integer id);

    List<Student> findByFullStudentNameStartingWith(String name);
    Optional<Student> findAllByFullStudentName(String fullName);
}
