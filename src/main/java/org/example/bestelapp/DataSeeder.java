package org.example.bestelapp;

import org.example.bestelapp.Model.*;
import org.example.bestelapp.Repository.CategoryDAO;
import org.example.bestelapp.Repository.RoleDAO;
import org.example.bestelapp.Repository.UserDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initRoles(RoleDAO roleDAO, UserDAO userDAO, CategoryDAO categoryDAO) {
        return args -> {
            if (roleDAO.count() == 0){
                roleDAO.save(new Role(0, "admin"));
                roleDAO.save(new Role(0, "technieker"));
            }
            if (userDAO.count() == 0) {
                Optional<Role> adminRole = roleDAO.findAll().stream()
                        .filter(r -> r.getName().equalsIgnoreCase("admin"))
                        .findFirst();

                if (adminRole.isPresent()) {
                    User admin = new User();
                    admin.setEmail("admin@aquafin.be");
                    admin.setPassword("admin123"); //deze nog versleutelen maybe
                    admin.setRole(adminRole.get());

                    userDAO.save(admin);
                }
            }
            if (categoryDAO.count() == 0){
                categoryDAO.save(new Category(0, "Bevestigingsmateriaal"));
                categoryDAO.save(new Category(0, "Persoonlijke beschermingsmiddelen"));
                categoryDAO.save(new Category(0, "Gereedschap"));
                categoryDAO.save(new Category(0, "Technische onderhoudsmaterialen"));
                categoryDAO.save(new Category(0, "Specifieke Aquafin / riolering gerelateerde tools"));
                categoryDAO.save(new Category(0, "Diversen / Verbruiksgoederen"));
            }
        };
    }
}
