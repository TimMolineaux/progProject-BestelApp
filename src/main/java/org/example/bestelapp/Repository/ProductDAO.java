package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryId(int categoryId);
    List<Product> findTop6ByOrderByTimesOrderedDesc();
}
