package org.example.progprojectbestelapp1.Controller;

import jakarta.validation.Valid;
import org.example.progprojectbestelapp1.Model.Product;
import org.example.progprojectbestelapp1.Repository.CategoryDAO;
import org.example.progprojectbestelapp1.Repository.OrderItemDAO;
import org.example.progprojectbestelapp1.Repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/stock")
public class StockController {

    private final ProductDAO productDAO;
    private final OrderItemDAO orderItemDAO;
    private final CategoryDAO categoryDAO;

    public StockController(ProductDAO productDAO, OrderItemDAO orderItemDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.orderItemDAO = orderItemDAO;
        this.categoryDAO = categoryDAO;
    }

    // 🗂️ Toon overzicht van alle producten
    @GetMapping
    public String showStockOverview(Model model) {
        List<Product> producten = productDAO.findAll();
        model.addAttribute("producten", producten);
        model.addAttribute("nieuwProduct", new Product());
        model.addAttribute("categories", categoryDAO.findAll());
        return "productbeheer";
    }

    // 🗑️ Verwijder product (als het niet gekoppeld is aan bestellingen)
    @PostMapping("/delete/{id}")
    public String removeProduct(@PathVariable int id, RedirectAttributes redirectAttributes) {
        Product product = productDAO.findById(id).orElse(null);

        if (product != null && !orderItemDAO.findByProduct(product).isEmpty()) {
            redirectAttributes.addFlashAttribute("deleteError",
                    "Product kan niet verwijderd worden: het is nog gekoppeld aan een bestelling.");
        } else if (product != null) {
            productDAO.delete(product);
            redirectAttributes.addFlashAttribute("deleteSuccess", "Product succesvol verwijderd.");
        }

        return "redirect:/stock";
    }

    // ➕ Voeg nieuw product toe (met validatie)
    @PostMapping("/new")
    public String newProduct(@ModelAttribute("nieuwProduct") @Valid Product nieuwProduct,
                             RedirectAttributes redirectAttributes) {

        if (nieuwProduct.getCategory() == null || nieuwProduct.getName() == null || nieuwProduct.getName().isBlank()) {
            redirectAttributes.addFlashAttribute("error", "Vul alle verplichte velden in.");
            return "redirect:/stock";
        }

        productDAO.save(nieuwProduct);
        redirectAttributes.addFlashAttribute("success", "Product succesvol toegevoegd.");
        return "redirect:/stock";
    }

    // ⭐ Toggle 'populair'-status van product
    @PostMapping("/togglePopular/{id}")
    public String togglePopular(@PathVariable int id, RedirectAttributes redirectAttributes) {
        productDAO.findById(id).ifPresent(product -> {
            product.setPopular(!product.isPopular());
            productDAO.save(product);
        });
        redirectAttributes.addFlashAttribute("success", "Populaire status aangepast.");
        return "redirect:/stock";
    }

    // ➕ Voeg extra voorraad toe aan bestaand product
    @PostMapping("/add/{id}")
    public String voegVoorraadToe(@PathVariable int id,
                                  @RequestParam("extraStock") int extraStock,
                                  RedirectAttributes redirectAttributes) {

        Product product = productDAO.findById(id).orElse(null);

        if (product == null) {
            redirectAttributes.addFlashAttribute("error", "Product niet gevonden.");
        } else if (extraStock < 1) {
            redirectAttributes.addFlashAttribute("error", "Voer een geldig aantal in (minstens 1).");
        } else {
            int origineleStock = product.getStock();
            product.setStock(origineleStock + extraStock);
            productDAO.save(product);

            redirectAttributes.addFlashAttribute("success",
                    "Voorraad verhoogd van " + origineleStock + " naar " + product.getStock() + ".");
        }

        return "redirect:/stock";
    }
}
