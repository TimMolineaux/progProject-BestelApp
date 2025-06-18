package org.example.progprojectbestelapp1.Repository;

import org.example.progprojectbestelapp1.Model.OrderItem;
import org.example.progprojectbestelapp1.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByProduct(Product product);
}