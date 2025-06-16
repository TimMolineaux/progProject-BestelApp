package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Integer> {
}
