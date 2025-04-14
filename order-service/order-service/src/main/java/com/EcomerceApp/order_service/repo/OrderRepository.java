package com.EcomerceApp.order_service.repo;

import com.EcomerceApp.order_service.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
