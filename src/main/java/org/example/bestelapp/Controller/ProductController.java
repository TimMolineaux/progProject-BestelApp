package org.example.bestelapp.Controller;

import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductDAO productDAO;

    // Haal producten op, met optionele zoekterm en popular filter
    @GetMapping
    public List<Product> getProducts(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "popular", required = false) Boolean popular
    ) {
        if (popular != null) {
            if (search != null && !search.isEmpty()) {
                if (popular) {
                    return productDAO.findByNameContainingIgnoreCaseAndPopularTrue(search);
                } else {
                    return productDAO.findByNameContainingIgnoreCaseAndPopularFalse(search);
                }
            } else {
                if (popular) {
                    return productDAO.findByPopularTrue();
                } else {
                    return productDAO.findByPopularFalse();
                }
            }
        } else {
            // Geen popular filter, haal alle producten
            if (search != null && !search.isEmpty()) {
                return productDAO.findByNameContainingIgnoreCase(search);
            } else {
                return productDAO.findAll();
            }
        }
    }

    // Debug endpoint: alle producten met popular status
    @GetMapping("/all")
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    // Haal product op via ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id) {
        Optional<Product> product = productDAO.findById(id);
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Voeg nieuw product toe
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productDAO.save(product);
        return ResponseEntity.ok(savedProduct);
    }

    // Verwijder product via ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable int id) {
        if (productDAO.existsById(id)) {
            productDAO.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
