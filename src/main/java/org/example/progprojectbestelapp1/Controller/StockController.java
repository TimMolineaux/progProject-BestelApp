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

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
    public String showStockOverview(@RequestParam(required = false) Integer category,
                                    Model model) {

        List<Product> producten;

        if (category != null && category != 0) {
            producten = productDAO.findByCategoryId(category);
        } else {
            producten = productDAO.findAll();
        }

        // Sorteer alfabetisch op naam
        producten.sort(Comparator.comparing(p -> p.getName().toLowerCase()));

        // Sorteer categorieën ook alfabetisch
        List<org.example.progprojectbestelapp1.Model.Category> gesorteerdeCategorieen = categoryDAO.findAll().stream()
                .sorted(Comparator.comparing(c -> c.getName().toLowerCase()))
                .toList();

        // Groepeer producten per categorie
        Map<String, List<Product>> productenPerCategorie = new LinkedHashMap<>();
        for (var cat : gesorteerdeCategorieen) {
            List<Product> catProducten = producten.stream()
                    .filter(p -> p.getCategory() != null && p.getCategory().getId() == cat.getId())
                    .toList();
            if (!catProducten.isEmpty()) {
                productenPerCategorie.put(cat.getName(), catProducten);
            }
        }

        model.addAttribute("producten", producten); // voor backwards compatibility
        model.addAttribute("productenPerCategorie", productenPerCategorie);
        model.addAttribute("categories", gesorteerdeCategorieen);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("nieuwProduct", new Product());
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
