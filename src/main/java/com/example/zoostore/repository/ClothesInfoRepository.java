package com.example.zoostore.repository;

import com.example.zoostore.model.ClothesInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClothesInfoRepository extends JpaRepository<ClothesInfo, Long> {
    @Query(value = "SELECT * FROM clothes_info cl" +
            "INNER JOIN products p on p.id = cl.product_id" +
            "INNER JOIN categories c on c.id = p.category_id" +
            "WHERE c.super_category_id = 2",
            nativeQuery = true)
    List<ClothesInfo> getAllClothes();
}
