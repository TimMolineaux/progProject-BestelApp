package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {
}
