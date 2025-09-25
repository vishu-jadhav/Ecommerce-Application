package com.EcomerceApp.inventory_service.repo;

import com.EcomerceApp.inventory_service.model.FailedEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {
}
