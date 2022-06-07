package com.example.zoostore.repository;

import com.example.zoostore.model.ClothesInfo;
import com.example.zoostore.model.PetsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClothesInfoRepository extends JpaRepository<ClothesInfo, Long> {
    @Query(value = "SELECT * FROM clothes_info cl\n" +
            "INNER JOIN products p on p.id = cl.product_id\n" +
            "INNER JOIN categories c on c.id = p.category_id\n" +
            "WHERE c.super_category_id = 2;",
            nativeQuery = true)
    List<ClothesInfo> getAllClothes();

    @Query(value = "SELECT * FROM clothes_info cl\n" +
            "INNER JOIN products p on p.id = cl.product_id\n" +
            "WHERE p.category_id = ?",
            nativeQuery = true)
    List<ClothesInfo> getAllClothesByCategoryId(Long id);

    void deleteClothesInfoByProductId(Long id);

    Optional<ClothesInfo> findByProductId(Long id);
}
