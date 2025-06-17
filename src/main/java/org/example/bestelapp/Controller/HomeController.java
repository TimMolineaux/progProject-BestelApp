package org.example.bestelapp.Controller;

import org.example.bestelapp.Service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private final ProductService productService;

    public HomeController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("popularProducts", productService.getPopularProducts(search));
        model.addAttribute("otherProducts", productService.getOtherProducts(search));
        model.addAttribute("searchTerm", search); // handig voor value terugvullen in zoekveld
        return "index";
    }
}
