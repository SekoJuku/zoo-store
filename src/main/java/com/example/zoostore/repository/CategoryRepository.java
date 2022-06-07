package com.example.zoostore.repository;

import com.example.zoostore.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM categories c\n" +
            "INNER JOIN super_categories sp on sp.id = c.super_category_id\n" +
            "WHERE sp.id = ?",
            nativeQuery = true)
    List<Category> findBySuperCategoryId(Long id);
}