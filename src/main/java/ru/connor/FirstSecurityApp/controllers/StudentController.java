package ru.connor.FirstSecurityApp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.connor.FirstSecurityApp.model.Student;
import ru.connor.FirstSecurityApp.services.StudentService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @GetMapping()
    public String showAllClasses(Model model) {
        model.addAttribute("students", studentService.showAllStudent());
        return "main/AllClasses";
    }


    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model){
        Optional<Student> student = Optional.ofNullable(studentService.showStudentById(id));
        if (student.isEmpty()){
            return "main/students/errorPage";
        }else model.addAttribute("student", student);
        return "main/students/index";
    }

    @GetMapping("/add")
    public String addStudent(@ModelAttribute("student") Student student) {
        return "main/students/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("student") @Valid Student student,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "main/students/new";

        studentService.addStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("student", studentService.showStudentById(id));
        return "main/students/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@ModelAttribute("student") @Valid Student student, BindingResult bindingResult, @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            return "main/students/edit";}
        studentService.update(id, student);
        return "redirect:/students";
    }

    @PostMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        Optional<Student> student = Optional.ofNullable(studentService.showStudentById(id));
        if (student.isPresent()){
            studentService.delete(id);
            return "redirect:/students";
        }
        return "main/students/index";
    }
}
