package com.example.zoostore.repository;

import com.example.zoostore.model.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderProductsRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> getAllByOrderId(Long id);
}
