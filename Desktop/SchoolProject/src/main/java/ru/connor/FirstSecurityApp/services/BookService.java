package ru.connor.FirstSecurityApp.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.connor.FirstSecurityApp.model.Book;
import ru.connor.FirstSecurityApp.model.Student;
import ru.connor.FirstSecurityApp.repository.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {


    private final BooksRepository booksRepository;

    public List<Book> showAllBooks() {
        return booksRepository.findAll();
    }

    public Book showBookById(int id) {
        Optional<Book> book = booksRepository.findById(id);
        return book.orElse(null);
    }

    public List<Book> searchByTitle(String bookName){
        return booksRepository.findByTitleStartingWith(bookName);
    }

    @Transactional
    public void addBook(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public boolean deleteBook(int id){
        if (booksRepository.findById(id).isPresent()){
            booksRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public void editBook(int id, Book book){
        Book bookToUpdated = booksRepository.findById(id).get();
        book.setBook_id(id);
        book.setOwner(bookToUpdated.getOwner());
        booksRepository.save(book);
    }

    public Student getBookOwner(int id){
        return booksRepository.findById(id).map(Book::getOwner).orElse(null);
    }

    @Transactional
    public void release(int id){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                }
        );
    }

    @Transactional
    public void assign(int id, Student selectedStudent){
        booksRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(selectedStudent);
                    book.setTakenAt(new Date());
                }
        );
    }
}
