package com.example.waikan.controllers;

import com.example.waikan.models.CartItem;
import com.example.waikan.repositories.CartItemRepository;
import com.example.waikan.repositories.ShoppingCartRepository;
import com.example.waikan.services.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class CartController {
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    @PostMapping("/addToCart/{id}")
    private String addProductToCart(@PathVariable long id){
        shoppingCartService.addShoppingCartFirstTime(id);
        return "redirect:/";
    }

    @GetMapping("/cart")
    private String getAllCarts(Model model, CartItem cartItem){
        model.addAttribute("products", cartItemRepository.findByProduct_Id(cartItem.getId()));
        return "cart";
    }
}
