package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
