package org.example.progprojectbestelapp1.Repository;

import org.example.progprojectbestelapp1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(int categoryId);
}