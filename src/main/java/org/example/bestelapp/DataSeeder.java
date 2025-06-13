package org.example.bestelapp;

import org.example.bestelapp.Model.Role;
import org.example.bestelapp.Model.RoleDAO;
import org.example.bestelapp.Model.User;
import org.example.bestelapp.Model.UserDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initRoles(RoleDAO roleDAO, UserDAO userDAO) {
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
        };
    }
}
