package org.example.bestelapp.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }
    @PostMapping("/reset-request")
    public String handleResetRequest(@RequestParam("email") String email) {
        // Hier zou je je reset-email logica toevoegen
        System.out.println("Reset-aanvraag voor: " + email);

        // Stuur gebruiker naar de bevestigingspagina
        return "reset-confirmation";
    }
    @GetMapping("/bestellingen")
    public String bestellingenPagina() {
        return "bestellingen";
    }

}
