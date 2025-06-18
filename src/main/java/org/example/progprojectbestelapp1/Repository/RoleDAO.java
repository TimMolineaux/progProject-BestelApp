package org.example.progprojectbestelapp1.Repository;

import org.example.progprojectbestelapp1.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDAO extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}