package org.example.bestelapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private List<String> accounts = new ArrayList<>();

    @GetMapping("/admin")
    public String adminPagina(Model model) {
        model.addAttribute("accounts", accounts);
        return "admin";
    }

    @PostMapping("/admin")
    public String voegAccountToe(@RequestParam("gebruikersnaam") String gebruikersnaam, Model model) {
        accounts.add(gebruikersnaam);
        model.addAttribute("accounts", accounts);
        return "admin"; // zelfde pagina opnieuw tonen met nieuwe data
    }
}
