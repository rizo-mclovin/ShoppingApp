package com.example.waikan.services;

import com.example.waikan.facades.ImageFacade;
import com.example.waikan.models.Image;
import com.example.waikan.models.Product;
import com.example.waikan.models.Review;
import com.example.waikan.models.User;
import com.example.waikan.repositories.ImageRepository;
import com.example.waikan.repositories.ProductRepository;
import com.example.waikan.repositories.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
public class ProductService {
    private static final Logger log = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final ImageFacade imageFacade;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository,
                          ImageRepository imageRepository,
                          ImageFacade imageFacade,
                          ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.imageFacade = imageFacade;
        this.reviewRepository = reviewRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(User user, Product product, MultipartFile file1, MultipartFile file2,
                            MultipartFile file3)
            throws IOException {
        product.setUser(user);
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = imageFacade.toEntity(file1);
            image1.setPreviewImage(1);
            image1.setBytes(compressBytes(image1.getBytes()));
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            image2 = imageFacade.toEntity(file2);
            image2.setPreviewImage(0);

            image2.setBytes(compressBytes(image2.getBytes()));
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            image3 = imageFacade.toEntity(file3);
            image3.setPreviewImage(0);
            image3.setBytes(compressBytes(image3.getBytes()));
            product.addImageToProduct(image3);
        }
        log.info("Saving new Product. Name: {}, Description: {}, Author: {}",
                product.getName(), product.getDescription(), product.getUser().getNikName());
        Product productFromDb = productRepository.save(product);
        productFromDb.setPreviewImageId(productFromDb.getImages().get(0).getId());
        productRepository.save(product);
    }

    public Image getImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElse(null);
        image.setBytes(decompressBytes(image.getBytes()));
        return image;
    }

    public void addReviewToProduct(Review review, Long productId) {
        Product product = productRepository.findById(productId)
                .orElse(null);
        if (product != null) {
            product.addReviewToProduct(review);
            productRepository.save(product);
        } else {
            log.error("Product with id {} is not found", productId);
        }
    }

    private static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException | DataFormatException e) {
            log.error("Cannot decompress Bytes");
        }
        return outputStream.toByteArray();
    }

    private byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            log.error("Cannot compress Bytes");
        }
        System.out.println("Compressed Image Byte Size - "
                + outputStream.toByteArray().length);
        return outputStream.toByteArray();
    }

    public List<Product> getProductsByUserId(Long userId) {
        return productRepository.getProductsByUserId(userId);
    }

}
