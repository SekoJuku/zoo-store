package com.example.zoostore.repository;

import com.example.zoostore.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> getAllByProductId(Long id);
}