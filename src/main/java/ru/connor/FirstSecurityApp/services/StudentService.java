package ru.connor.FirstSecurityApp.services;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.connor.FirstSecurityApp.model.Student;
import ru.connor.FirstSecurityApp.repository.StudentsRepository;

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


}