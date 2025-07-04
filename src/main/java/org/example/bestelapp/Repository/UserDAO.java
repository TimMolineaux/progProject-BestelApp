package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

}
