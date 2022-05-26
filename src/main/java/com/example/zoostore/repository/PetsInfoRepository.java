package com.example.zoostore.repository;

import com.example.zoostore.model.PetsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PetsInfoRepository extends JpaRepository<PetsInfo, Long> {
    Optional<PetsInfo> findByProductId(Long id);

    void deleteByProductId(Long id);

    @Query(value = "SELECT * FROM pets_info pe\n" +
            "INNER JOIN products p on p.id = pe.product_id\n" +
            "INNER JOIN categories c on c.id = p.category_id\n" +
            "WHERE c.super_category_id = 1;",
            nativeQuery = true)
    List<PetsInfo> getAllPets();

    @Query(value = "SELECT * FROM pets_info pe\n" +
            "INNER JOIN products p on p.id = pe.product_id\n" +
            "WHERE p.category_id = ?",
            nativeQuery = true)
    List<PetsInfo> getAllPetsByCategoryId(Long id);
}
