package ru.connor.shopping_app.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.connor.shopping_app.model.Product;
import ru.connor.shopping_app.service.ProductService;

import java.io.IOException;

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
    public String productInfo(@PathVariable("id") Long id, Model model) {
        Product product = (Product) productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("images", product.getImages());
        System.out.println(product.getImages().get(0).getId());
        return "product/info";
    }

    @PostMapping("product/create")
    public String createProduct(Product product, BindingResult bindingResult,
                                @RequestParam("file1")MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        if (bindingResult.hasErrors()){
            return "product/create";
        }else productService.saveProduct(product, file1, file2, file3);

        return "redirect:/";
    }

    @PostMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id){
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
