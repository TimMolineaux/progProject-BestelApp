package org.example.bestelapp.Controller;

import org.example.bestelapp.Repository.CategoryDAO;
import org.example.bestelapp.Repository.OrderItemDAO;
import org.springframework.ui.Model;
import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    private ProductDAO productDAO;
    private OrderItemDAO orderItemDAO;
    private CategoryDAO categoryDAO;

    public StockController(ProductDAO productDAO, OrderItemDAO orderItemDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.orderItemDAO = orderItemDAO;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public String showStockOverview(@RequestParam(required = false) Integer category,
                                    @RequestParam(required = false) String search,
                                    Model model) {
        List<Product> producten;

        if (search != null && !search.isEmpty()) {
            producten = productDAO.findByNameContainingIgnoreCase(search);
        } else if (category != null && category != 0) {
            producten = productDAO.findByCategoryId(category);
        } else {
            producten = productDAO.findAll();
        }

        var productenPerCategorie = new java.util.LinkedHashMap<String, List<Product>>();
        categoryDAO.findAll().forEach(cat -> {
            var perCategorie = producten.stream()
                    .filter(p -> p.getCategory().getId() == cat.getId())
                    .toList();
            if (!perCategorie.isEmpty()) {
                productenPerCategorie.put(cat.getName(), perCategorie);
            }
        });

        model.addAttribute("productenPerCategorie", productenPerCategorie);
        model.addAttribute("nieuwProduct", new Product());
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchTerm", search);

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

    @PostMapping("/new")
    public String newProduct(@ModelAttribute("nieuwProduct") Product nieuwProduct,
                                 RedirectAttributes redirectAttributes) {
        if (nieuwProduct.getCategory() == null || nieuwProduct.getName() == null) {
            redirectAttributes.addFlashAttribute("error", "Vul alle verplichte velden in.");
            return "redirect:/stock";
        }

        productDAO.save(nieuwProduct);
        redirectAttributes.addFlashAttribute("success", "Product succesvol toegevoegd.");
        return "redirect:/stock";
    }

    @PostMapping("/add/{id}")
    public String addStock(@PathVariable int id,
                           @RequestParam("extraStock") int extraStock,
                           RedirectAttributes redirectAttributes) {
        productDAO.findById(id).ifPresent(product -> {
            product.setStock(product.getStock() + extraStock);
            productDAO.save(product);
        });
        redirectAttributes.addFlashAttribute("success", "Voorraad succesvol aangevuld.");
        return "redirect:/stock";
    }

}
