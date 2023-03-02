package ru.connor.FirstSecurityApp.controllers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.connor.FirstSecurityApp.model.Administration;
import ru.connor.FirstSecurityApp.model.Student;
import ru.connor.FirstSecurityApp.services.PersonDetailsService;
import ru.connor.FirstSecurityApp.services.StudentService;

@RequiredArgsConstructor
@Controller
public class MainPageController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/choiceBooksOrStudents")
    public String showChoice() {
        return "main/choice";
    }



//    @GetMapping("/books")
//    public String showAllBooks() {
//        return "main/books";
//    }


}