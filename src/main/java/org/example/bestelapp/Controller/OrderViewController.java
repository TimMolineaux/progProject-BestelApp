package org.example.bestelapp.Controller;

import org.example.bestelapp.Model.Order;
import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.OrderDAO;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderViewController {

    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    public OrderViewController(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public String showOrders(Model model) {
        List<Order> alleOrders = orderDAO.findAllWithItemsAndProducts();

        // Filter alleen "pending" bestellingen
        List<Order> openOrders = alleOrders.stream()
                .filter(order -> "pending".equals(order.getStatus()))
                .toList();

        model.addAttribute("orders", openOrders);
        return "bestellingsbeheer";
    }

    @PostMapping("/complete/{id}")
    public String markOrderCompleted(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Order order = orderDAO.findById(id).orElse(null);

        if (order == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bestelling niet gevonden.");
            return "redirect:/orders";
        }

        if ("complete".equals(order.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bestelling is al voltooid.");
            return "redirect:/orders";
        }

        boolean voorraadTekort = false;
        for (var item : order.getOrderItems()) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                voorraadTekort = true;
                break;
            }
        }

        if (voorraadTekort) {
            redirectAttributes.addFlashAttribute("errorMessage", "Niet genoeg voorraad om deze bestelling te voltooien.");
            return "redirect:/orders";
        }

        for (var item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productDAO.save(product);
        }

        order.setStatus("complete");
        orderDAO.save(order);

        redirectAttributes.addFlashAttribute("successMessage", "Bestelling gemarkeerd als voltooid en voorraad aangepast.");
        return "redirect:/orders";
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Order order = orderDAO.findById(id).orElse(null);

        if (order == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bestelling niet gevonden.");
            return "redirect:/orders";
        }

        order.setStatus("cancelled");
        orderDAO.save(order);

        redirectAttributes.addFlashAttribute("successMessage", "Bestelling geannuleerd.");
        return "redirect:/orders";
    }
}
