package org.example.progprojectbestelapp1.Repository;

import org.example.progprojectbestelapp1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}