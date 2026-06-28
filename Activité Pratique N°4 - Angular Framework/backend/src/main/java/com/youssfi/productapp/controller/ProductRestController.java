package com.youssfi.productapp.controller;

import com.youssfi.productapp.model.Product;
import com.youssfi.productapp.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
// Autorise explicitement les requêtes (GET, DELETE, OPTIONS) provenant de l'app Angular pour éviter le blocage CORS
@CrossOrigin(origins = "http://localhost:4200")
public class ProductRestController {

    private final ProductRepository productRepository;

    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/{id}/selected")
    public Product updateSelected(@PathVariable Long id, @RequestParam boolean value) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setSelected(value);
        return productRepository.save(product);
    }
}
