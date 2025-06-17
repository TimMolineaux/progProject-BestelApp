package org.example.bestelapp.Controller;

import org.example.bestelapp.Model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private final List<Product> producten = new ArrayList<>();

    @GetMapping("/producten")
    public String toonProducten(Model model) {
        model.addAttribute("producten", producten);
        model.addAttribute("nieuwProduct", new Product());
        return "producten";
    }

    @PostMapping("/producten/toevoegen")
    public String voegProductToe(@ModelAttribute Product product) {
        producten.add(product);
        return "redirect:/producten";
    }

    @PostMapping("/producten/verwijderen/{index}")
    public String verwijderProduct(@PathVariable int index) {
        producten.remove(index);
        return "redirect:/producten";
    }
}
