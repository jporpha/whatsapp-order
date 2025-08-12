package com.jp.orpha.whatsapp_order.repositories;

import com.jp.orpha.whatsapp_order.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByAvailableTrueOrderByPositionAscIdAsc();
    List<Product> findAllByOrderByPositionAscIdAsc();
}
