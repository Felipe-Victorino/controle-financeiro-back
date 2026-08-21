package com.finfin.backend.repository;

import com.finfin.backend.entity.Category;
import com.finfin.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from category c where c.owner = ?1")
    List<Category> getCategoriesByOwner(Long id);
}
