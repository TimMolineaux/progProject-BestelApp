package org.example.bestelapp.Controller;

import org.example.bestelapp.Model.Role;
import org.example.bestelapp.Model.User;
import org.example.bestelapp.Repository.RoleDAO;
import org.example.bestelapp.Repository.UserDAO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserViewController(UserDAO userDAO, RoleDAO roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showUserManagement(Model model) {
        List<User> users = userDAO.findAll();
        List<Role> roles = roleDAO.findAll();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", new User());

        return "gebruikersbeheer";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user, @RequestParam String roleName, Model model) {
        if (userDAO.findByEmail(user.getEmail()).isPresent()) {
            List<User> users = userDAO.findAll();
            List<Role> roles = roleDAO.findAll();

            model.addAttribute("users", users);
            model.addAttribute("roles", roles);
            model.addAttribute("newUser", user);
            model.addAttribute("emailError", "Deze e-mail wordt al gebruikt.");

            return "gebruikersbeheer";
        }

        Role role = roleDAO.findByName(roleName);
        if (role != null) {
            user.setRole(role);
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            userDAO.save(user);
        }

        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        if (id == 1) {
            List<User> users = userDAO.findAll();
            List<Role> roles = roleDAO.findAll();

            model.addAttribute("users", users);
            model.addAttribute("roles", roles);
            model.addAttribute("newUser", new User());
            model.addAttribute("deleteError", "De basis admin kan niet verwijderd worden.");

            return "gebruikersbeheer";
        }

        userDAO.deleteById(id);

        return "redirect:/users";
    }

    @PostMapping("/updateRole/{id}")
    public String updateUserRole(@PathVariable int id, @RequestParam String roleName) {
        User user = userDAO.findById(id).orElse(null);
        if (user != null) {
            Role role = roleDAO.findByName(roleName);
            if (role != null) {
                user.setRole(role);
                userDAO.save(user);
            }
        }
        return "redirect:/users";
    }

}
