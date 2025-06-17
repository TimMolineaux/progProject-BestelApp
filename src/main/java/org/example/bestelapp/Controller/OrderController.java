package org.example.bestelapp.Controller;

import org.example.bestelapp.Model.Order;
import org.example.bestelapp.Model.OrderItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/orders")

public class OrderController {

    @GetMapping("/bestellingen")
    public String getBestellingen(Model model) {
        List<OrderItem> items = Arrays.asList(
                new OrderItem("Oordoppen", 4),
                new OrderItem("Stofmaskers", 2)
        );

        List<Order> orders = Arrays.asList(
                new Order(2, LocalDate.of(2025, 6, 11), items),
                new Order(2, LocalDate.of(2025, 6, 11), items)
        );

        model.addAttribute("orders", orders);
        return "orders";
    }
}
