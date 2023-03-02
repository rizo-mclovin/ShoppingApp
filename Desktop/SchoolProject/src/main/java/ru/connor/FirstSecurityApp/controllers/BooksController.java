package ru.connor.FirstSecurityApp.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.connor.FirstSecurityApp.model.Book;
import ru.connor.FirstSecurityApp.model.Student;
import ru.connor.FirstSecurityApp.services.BookService;
import ru.connor.FirstSecurityApp.services.StudentService;

import javax.swing.text.html.Option;
import java.util.Optional;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BooksController {

    private final BookService bookService;
    private final StudentService studentService;

    @GetMapping()
    public String allBooks(@ModelAttribute("book") Book book, Model model){
        model.addAttribute("books", bookService.showAllBooks());
        return "main/books/all-books";
    }

    @GetMapping("/{id}")
    public String showOneBook(@PathVariable("id") int id, Model model, @ModelAttribute("book") Book book) {
        model.addAttribute("book", bookService.showBookById(id));
        Student bookOwner = bookService.getBookOwner(id);
        if (bookOwner != null){
            model.addAttribute("owner", bookOwner);
        }else {
            model.addAttribute("students", studentService.showAllStudent());
        }
        return "main/books/index";
    }



    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "main/books";
        }
        bookService.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookService.showBookById(id));
        return "main/books/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") int id,
                             @ModelAttribute("book") Book book, BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            return "main/books/edit";
        }
        bookService.editBook(id, book);
        return "redirect:/books";
    }


    @GetMapping("/search")
    public String searchPage(){return "main/books/search";}

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
        model.addAttribute("books", bookService.searchByTitle(query));
        return "main/books/search";
    }


    @PostMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id){
        Optional<Book> book = Optional.ofNullable(bookService.showBookById(id));
        if (book.isPresent()){
            bookService.deleteBook(id);
            return "redirect:/books";
        }
        return "/main/books/index";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable int id, @ModelAttribute("student") Student selectedStudent){
        bookService.assign(id, selectedStudent);
        return "redirect:/books/" + id;
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable int id){
        bookService.release(id);
        return "redirect:/books/" + id;
    }

}
