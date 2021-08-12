package com.example.waikan.services;

import com.example.waikan.facades.ImageFacade;
import com.example.waikan.models.Image;
import com.example.waikan.models.Product;
import com.example.waikan.models.User;
import com.example.waikan.repositories.ImageRepository;
import com.example.waikan.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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

    public ProductService(ProductRepository productRepository,
                          ImageRepository imageRepository,
                          ImageFacade imageFacade) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.imageFacade = imageFacade;
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

    public List<Product> getAllProducts(String searchWord, String searchCity) {
        List<Product> products = productRepository.findAll();
        if (!searchWord.equals("") && !searchCity.equals("")) {
            return products;
        }
        if (!searchWord.equals("")) {
            products = searchBySearchWord(searchWord, products);
        }
        if (!searchCity.equals("")) {
            products.removeIf(m -> !m.getCity().equals(searchCity));
        }
        return products;
    }

    private List<Product> searchBySearchWord(String searchWord, List<Product> products) {
        List<Product> searchProducts = new ArrayList<>();
        String lowerSearchWord = makeStringToLowerCase(searchWord);
        char[] searchWordToCharArray = lowerSearchWord.toCharArray();
        for (int a = 0; a < products.size(); a++) {
            String lowerProductName = makeStringToLowerCase(products.get(a).getName());
            char[] chars = lowerProductName.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < searchWordToCharArray.length; j++) {
                    try {
                        if (chars[i] == searchWordToCharArray[j]) {
                            if (chars[i + 1] == searchWordToCharArray[j + 1]) {
                                if (chars[i + 2] == searchWordToCharArray[j + 2]) {
                                    if (chars[i + 3] == searchWordToCharArray[j + 3]) {
                                        if (!searchProducts.contains(products.get(a))) {
                                            searchProducts.add(products.get(a));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
            }
        }
        return searchProducts;
    }

    private String makeStringToLowerCase(String word) {
        String lowerString = "";
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            lowerString += Character.toLowerCase(c);
        }
        return lowerString;
    }

    public void saveProduct(User user, Product product, MultipartFile file1, MultipartFile file2,
                            MultipartFile file3)
            throws IOException {
        product.setUser(user);
        product.setPrice(product.getPrice() + " ₽");
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

    public void deleteProduct(User user, Long id) {
        Product product = productRepository.findById(id)
                .orElse(null);
        if (product != null) {
            if (product.getUser().getId().equals(user.getId())) {
                productRepository.delete(product);
                log.info("Product with id = {} was deleted", id);
            } else {
                log.error("User: {} haven't this product with id = {}", user.getEmail(), id);
            }
        } else {
            log.error("Product with id = {} is not found", id);
        }
    }

}
