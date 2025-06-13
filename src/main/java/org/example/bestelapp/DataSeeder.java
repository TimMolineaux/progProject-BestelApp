package org.example.bestelapp;

import org.example.bestelapp.Model.Role;
import org.example.bestelapp.Model.RoleDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initRoles(RoleDAO roleDAO) {
        return args -> {
            if (roleDAO.count() == 0){
                roleDAO.save(new Role(0, "admin"));
                roleDAO.save(new Role(0, "technieker"));
            }
        };
    }
}
