package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDAO extends JpaRepository<Order, Integer> {
}
