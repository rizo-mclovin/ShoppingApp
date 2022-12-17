package ru.connor.shopping_app.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.connor.shopping_app.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
