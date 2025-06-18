package org.example.progprojectbestelapp1.Repository;

import org.example.progprojectbestelapp1.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    @Query("SELECT DISTINCT o FROM Order o LEFT JOIN FETCH o.orderItems i LEFT JOIN FETCH i.product")
    List<Order> findAllWithItemsAndProducts();

}