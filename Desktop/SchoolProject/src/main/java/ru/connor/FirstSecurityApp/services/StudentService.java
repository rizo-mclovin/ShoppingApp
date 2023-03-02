package ru.connor.FirstSecurityApp.services;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.connor.FirstSecurityApp.model.Book;
import ru.connor.FirstSecurityApp.model.Student;
import ru.connor.FirstSecurityApp.repository.StudentsRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@SuppressWarnings("unused")
@Transactional(readOnly = true)
public class StudentService {
    private final StudentsRepository studentsRepository;

    public List<Student> showAllStudent(){
        return studentsRepository.findAll();
    }

    public Student showStudentById(int id){
        Optional<Student> foundPerson = studentsRepository.findById(id);

        return foundPerson.orElse(null);
    }

    @Transactional
    public void addStudent(Student student){
        studentsRepository.save(student);
    }

    @Transactional
    public void update(int id, Student person){
        person.setId(id);
        studentsRepository.save(person);
    }

    @Transactional
    public boolean delete(int id){
        if (studentsRepository.findById(id).isPresent()){
            studentsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Student> getStudentByFullName(String fullName){
        return studentsRepository.findAllByFullStudentName(fullName);
    }

    public List<Book> getBooksByPersonId(int id){
        Optional<Student> student = studentsRepository.findById(id);
        if (student.isPresent()){
            Hibernate.initialize(student.get().getBooks());
            student.get().getBooks().forEach(book -> {
                long diffInMills = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMills > 864000000){
                    book.setExpired(true);
                }
            });
            return student.get().getBooks();
        }else return Collections.emptyList();

    }

}