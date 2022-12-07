package ru.connor.FirstSecurityApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.connor.FirstSecurityApp.model.Product;
import ru.connor.FirstSecurityApp.repository.ProductRepository;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts(String title){
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public void saveProduct(Product product){
        log.info("Добавлен новый продукт {}", product);
        productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


    public Object getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }


}
