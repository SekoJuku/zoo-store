package com.example.zoostore.repository;

import com.example.zoostore.model.ClothesInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClothesInfoRepository extends JpaRepository<ClothesInfo, Long> {
}
