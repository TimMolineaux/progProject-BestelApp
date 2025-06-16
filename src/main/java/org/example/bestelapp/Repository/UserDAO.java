package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
}