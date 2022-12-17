package ru.connor.shopping_app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.connor.shopping_app.model.Product;
import ru.connor.shopping_app.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void addProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProductById(long id){
        productRepository.deleteById(id);
    }

    public Optional<Product> getProductById(long id){
        return productRepository.findById(id);
    }

    public List<Product> getAllProductsByCategoryId(int id){
        return productRepository.findByCategory_Id(id);
    }
}
