package org.example.progprojectbestelapp1.Repository;

import org.example.progprojectbestelapp1.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDAO extends JpaRepository<Category, Integer> {
}