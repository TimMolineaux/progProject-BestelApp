
package org.example.progprojectbestelapp1.Controller;

import org.example.progprojectbestelapp1.Model.Role;
import org.example.progprojectbestelapp1.Model.User;
import org.example.progprojectbestelapp1.Repository.RoleDAO;
import org.example.progprojectbestelapp1.Repository.UserDAO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserViewController {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    public UserViewController(UserDAO userDAO, RoleDAO roleDAO) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
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
    public String addUser(@ModelAttribute User user, @RequestParam String roleName) {
        Role role = roleDAO.findByName(roleName);
        if (role != null) {
            user.setRole(role);
            userDAO.save(user);
        }

        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userDAO.deleteById(id);

        return "redirect:/users";
    }

}
