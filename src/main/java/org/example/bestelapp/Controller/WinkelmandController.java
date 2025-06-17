package org.example.bestelapp.Controller;

import org.example.bestelapp.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/winkelmand")
@SessionAttributes("winkelmand")
public class WinkelmandController {

    private final ProductService productService;

    public WinkelmandController(ProductService productService) {
        this.productService = productService;
    }

    @ModelAttribute("winkelmand")
    public Map<Long, Integer> winkelmand() {
        return new HashMap<>();
    }

    @GetMapping
    public String winkelmandView(Model model, @ModelAttribute("winkelmand") Map<Long, Integer> winkelmand) {
        // Optioneel: productinformatie toevoegen aan model voor winkelmand view
        model.addAttribute("winkelmand", winkelmand);
        return "winkelmand"; // Maak een Thymeleaf template winkelmand.html aan
    }

    @PostMapping("/toevoegen")
    public String voegToe(@RequestParam Long productId, @RequestParam int aantal,
                          @ModelAttribute("winkelmand") Map<Long, Integer> winkelmand) {
        winkelmand.merge(productId, aantal, Integer::sum);
        return "redirect:/";
    }

    @PostMapping("/clear")
    public String leegWinkelmand(SessionStatus status) {
        status.setComplete();
        return "redirect:/";
    }
}
