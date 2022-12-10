package ru.connor.shopping_app.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.connor.shopping_app.model.Image;
import ru.connor.shopping_app.model.Product;
import ru.connor.shopping_app.repository.ProductRepository;
import java.io.IOException;
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

    public void saveProduct(Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        Image image1;
        Image image2;
        Image image3;

        if (file1.getSize() != 0){
            image1 = toImageEntity(file1);
            product.addImageToProduct(image1 );
        }
        if (file2.getSize() != 0){
            image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0){
            image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }


        log.info("Добавлен новый продукт {}", product);
        Product productFromFB = productRepository.save(product);
        productFromFB.setPreviewImageId(productFromFB.getImages().get(0).getId());
        productRepository.save(product);
    }

    private Image toImageEntity(MultipartFile multipartFile) throws IOException {
        Image image = new Image();
        image.setName(multipartFile.getName());
        image.setOriginalFileName(multipartFile.getOriginalFilename());
        image.setContentType(multipartFile.getContentType());
        image.setSize(multipartFile.getSize());
        image.setBytes(multipartFile.getBytes());
        return image;
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }


    public Object getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }


}
