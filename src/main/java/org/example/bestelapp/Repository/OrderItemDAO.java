package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.OrderItem;
import org.example.bestelapp.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByProduct(Product product);
}
