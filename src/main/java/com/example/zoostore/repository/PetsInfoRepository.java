package com.example.zoostore.repository;

import com.example.zoostore.model.PetsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PetsInfoRepository extends JpaRepository<PetsInfo, Long> {
    Optional<PetsInfo> findByProductId(Long id);
    PetsInfo findPetsInfosByProductId(Long id);

    void deleteByProductId(Long id);
}
