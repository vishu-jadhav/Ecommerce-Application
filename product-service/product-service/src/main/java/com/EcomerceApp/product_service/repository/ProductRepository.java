package com.EcomerceApp.product_service.repository;

import com.EcomerceApp.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
