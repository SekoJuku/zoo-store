package com.example.zoostore.repository;

import com.example.zoostore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT * FROM products p\n" +
            "INNER JOIN categories c on c.id = p.category_id\n" +
            "WHERE c.super_category_id = 3",
            nativeQuery = true)
    List<Product> getProducts();

    Optional<Product> findProductById(Long id);

    @Query(value = "SELECT * FROM products p\n" +
            "WHERE p.category_id = ?;",
            nativeQuery = true)
    List<Product> getAllByCategoryId(Long id);
}