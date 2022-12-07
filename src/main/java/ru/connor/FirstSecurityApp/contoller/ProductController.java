package ru.connor.FirstSecurityApp.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.connor.FirstSecurityApp.model.Product;
import ru.connor.FirstSecurityApp.service.ProductService;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping()
    public String products(@RequestParam(name = "title", required = false) String title, Model model){
        model.addAttribute("products", productService.getAllProducts(title));
        return "product/list";
    }

    @GetMapping("product/{id}")
    public String productInfo(@PathVariable("id") Long id, Model model,
                                @ModelAttribute("product") Product product){
        model.addAttribute("product", productService.getProductById(id));
        return "product/info";
    }

    @PostMapping("product/create")
    public String createProduct(Product product, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "product/create";
        }else productService.saveProduct(product);

        return "redirect:/";
    }

    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
