package org.example.bestelapp.Service;

import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getPopularProducts(String searchTerm) {
        if (searchTerm != null && !searchTerm.isBlank()) {
            return productDAO.findByNameContainingIgnoreCaseAndPopularTrue(searchTerm);
        } else {
            return productDAO.findByPopularTrue();
        }
    }

    public List<Product> getOtherProducts(String searchTerm) {
        if (searchTerm != null && !searchTerm.isBlank()) {
            return productDAO.findByNameContainingIgnoreCaseAndPopularFalse(searchTerm);
        } else {
            return productDAO.findByPopularFalse();
        }
    }
}
