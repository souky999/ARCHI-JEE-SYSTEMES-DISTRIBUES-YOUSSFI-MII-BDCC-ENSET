package ma.enset.controllers;

import jakarta.validation.Valid;
import ma.enset.entities.Product;
import ma.enset.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ---- Liste avec pagination et recherche ----
    @GetMapping({"/", "/products"})
    public String listProducts(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            Model model) {

        Page<Product> productPage = productRepository.findByNameContainsIgnoreCase(keyword,
                PageRequest.of(page, size));

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pages", new int[productPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "products/list";
    }

    // ---- Supprimer un produit ----
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @RequestParam(defaultValue = "") String keyword,
                                @RequestParam(defaultValue = "0") int page) {
        productRepository.deleteById(id);
        return "redirect:/products?page=" + page + "&keyword=" + keyword;
    }

    // ---- Formulaire d'ajout ----
    @GetMapping("/products/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/form";
    }

    // ---- Formulaire d'édition ----
    @GetMapping("/products/edit/{id}")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit introuvable : " + id));
        model.addAttribute("product", product);
        return "products/form";
    }

    // ---- Enregistrer (ajout + mise à jour) ----
    @PostMapping("/products/save")
    public String saveProduct(@Valid @ModelAttribute Product product,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            return "products/form";
        }
        productRepository.save(product);
        return "redirect:/products";
    }
}
