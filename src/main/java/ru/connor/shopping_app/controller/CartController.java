package ru.connor.shopping_app.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.connor.shopping_app.global.GlobalData;
import ru.connor.shopping_app.model.Product;
import ru.connor.shopping_app.service.ProductService;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;

    @GetMapping("addTpCart/{id}")
    public String addTpCart(@PathVariable int id){
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/shop";

    }

    @GetMapping("/cart")
    public String cartGet(Model model){
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "cart";


    }
}
