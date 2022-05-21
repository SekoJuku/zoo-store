package com.example.zoostore.repository;

import com.example.zoostore.model.PetsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetsInfoRepository extends JpaRepository<PetsInfo, Long> {
}
