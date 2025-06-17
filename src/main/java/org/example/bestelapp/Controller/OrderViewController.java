package org.example.bestelapp.Controller;

import org.example.bestelapp.Model.Order;
import org.example.bestelapp.Repository.OrderDAO;
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

    public OrderViewController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @GetMapping
    public String showOrders(Model model) {
        List<Order> orders = orderDAO.findAllWithItemsAndProducts();
        model.addAttribute("orders", orders);
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

        order.setStatus(true);
        orderDAO.save(order);

        redirectAttributes.addFlashAttribute("successMessage", "Bestelling succesvol gemarkeerd als voltooid.");
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
