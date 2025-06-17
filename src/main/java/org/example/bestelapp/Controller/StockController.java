package org.example.bestelapp.Controller;

import org.example.bestelapp.Repository.OrderItemDAO;
import org.springframework.ui.Model;
import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    private ProductDAO productDAO;
    private OrderItemDAO orderItemDAO;

    public StockController(ProductDAO productDAO, OrderItemDAO orderItemDAO) {
        this.productDAO = productDAO;
        this.orderItemDAO = orderItemDAO;
    }

    @GetMapping
    public String showStockOverview(Model model) {
        List<Product> producten = productDAO.findAll();
        model.addAttribute("producten", producten);
        return "productbeheer";
    }

    @PostMapping("/delete/{id}")
    public String removeProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Product product = productDAO.findById(id).orElse(null);

        if (product != null && !orderItemDAO.findByProduct(product).isEmpty()) {
            redirectAttributes.addFlashAttribute("deleteError", "Product kan niet verwijderd worden: het is nog gekoppeld aan een bestelling.");
            return "redirect:/stock";
        }

        if (product != null) {
            productDAO.delete(product);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Product succesvol verwijderd.");
        }

        return "redirect:/stock";
    }

}
