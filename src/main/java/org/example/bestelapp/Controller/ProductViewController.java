package org.example.bestelapp.Controller;

import org.springframework.ui.Model;
import org.example.bestelapp.Model.Product;
import org.example.bestelapp.Repository.CategoryDAO;
import org.example.bestelapp.Repository.ProductDAO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/index")
public class ProductViewController {

    private ProductDAO productDAO;
    private CategoryDAO categoryDAO;

    public ProductViewController(ProductDAO productDAO, CategoryDAO categoryDAO) {
        this.productDAO = productDAO;
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public String showProducts(@RequestParam(required = false) String search,
                               @RequestParam(required = false) Integer category,
                               Model model) {

        List<Product> allProducts;

        if (search != null && !search.isEmpty()) {
            allProducts = productDAO.findByNameContainingIgnoreCase(search);
        } else if (category != null && category != 0) {
            allProducts = productDAO.findByCategoryId(category);
        } else {
            allProducts = productDAO.findAll();
        }

        List<Product> popularProducts = allProducts.stream()
                .filter(Product::isPopular)
                .toList();

        Map<String, List<Product>> productsByCategory = new LinkedHashMap<>();
        categoryDAO.findAll().forEach(cat -> {
            List<Product> categoryProducts = allProducts.stream()
                    .filter(p -> p.getCategory().getId() == cat.getId() && !p.isPopular())
                    .toList();
            if (!categoryProducts.isEmpty()) {
                productsByCategory.put(cat.getName(), categoryProducts);
            }
        });

        model.addAttribute("popularProducts", popularProducts);
        model.addAttribute("productsByCategory", productsByCategory);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchTerm", search);

        return "index";
    }
}
