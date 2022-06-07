package com.example.zoostore.repository;

import com.example.zoostore.model.SuperCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuperCategoryRepository extends JpaRepository<SuperCategory, Long> {
}