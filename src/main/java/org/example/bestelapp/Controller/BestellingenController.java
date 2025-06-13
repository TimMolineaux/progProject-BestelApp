package org.example.bestelapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BestellingenController {

    @GetMapping("/bestellingen")
    public String bestellingen() {
        return "bestellingen"; // verwijst naar bestellingen.html in /templates
    }
}
