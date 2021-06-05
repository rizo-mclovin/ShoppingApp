package com.example.waikan.controllers;

import com.example.waikan.models.Review;
import com.example.waikan.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewController {
    private final ProductService productService;

    public ReviewController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/review/create")
    public String createReview(Review review,
                               @RequestParam("productId") Long productId) {
        productService.addReviewToProduct(review, productId);
        return "redirect:/product/" + productId;
    }
}
