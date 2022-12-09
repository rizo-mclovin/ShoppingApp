package ru.connor.FirstSecurityApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.connor.FirstSecurityApp.model.Image;

public interface ImagesRepository extends JpaRepository<Image, Long> {
}
