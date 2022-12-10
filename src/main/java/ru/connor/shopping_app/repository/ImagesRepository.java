package ru.connor.shopping_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.connor.shopping_app.model.Image;

public interface ImagesRepository extends JpaRepository<Image, Long> {
}
