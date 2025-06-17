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

    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderViewController(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @GetMapping
    public String showOrders(Model model) {
        List<Order> alleOrders = orderDAO.findAllWithItemsAndProducts();
        List<Order> openOrders = alleOrders.stream()
                .filter(order -> !order.isStatus())
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

        if (order.isStatus()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Bestelling is al voltooid.");
            return "redirect:/orders";
        }

        // Voorraad verminderen
        boolean voorraadTekort = false;
        for (var item : order.getOrderItems()) {
            Product product = item.getProduct();
            int nieuweVoorraad = product.getStock() - item.getQuantity();

            if (nieuweVoorraad < 0) {
                voorraadTekort = true;
                break;
            }
        }

        if (voorraadTekort) {
            redirectAttributes.addFlashAttribute("errorMessage", "Niet genoeg voorraad om deze bestelling te voltooien.");
            return "redirect:/orders";
        }

        // Als er genoeg voorraad is, verminderen en opslaan
        for (var item : order.getOrderItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productDAO.save(product);
        }

        // Bestelling als voltooid markeren
        order.setStatus(true);
        orderDAO.save(order);

        redirectAttributes.addFlashAttribute("successMessage", "Bestelling succesvol gemarkeerd als voltooid en voorraad aangepast.");
        return "redirect:/orders";
    }

    @PostMapping("/cancel/{id}")
    public String cancelOrder(@PathVariable int id, RedirectAttributes redirectAttributes) {
        try {
            orderDAO.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Bestelling succesvol geannuleerd.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Fout bij annuleren van bestelling.");
        }
        return "redirect:/orders";
    }
}
