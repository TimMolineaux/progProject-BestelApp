package org.example.bestelapp.Repository;

import org.example.bestelapp.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}
