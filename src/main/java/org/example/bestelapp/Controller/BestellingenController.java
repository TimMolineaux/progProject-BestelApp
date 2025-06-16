package org.example.bestelapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BestellingenController {

    @GetMapping("/bestellingen")
    public String getBestellingen() {
        // Geeft de naam van je Thymeleaf-template terug (bestellingen.html zonder extensie)
        return "bestellingen";
    }
}
