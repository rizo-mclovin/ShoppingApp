package ru.connor.FirstSecurityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.FirstSecurityApp.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("all")
public interface BooksRepository extends JpaRepository<Book, Integer> {
    List<Book> findByTitleStartingWith(String title);
    @Override
    Optional<Book> findById(Integer id);
}
