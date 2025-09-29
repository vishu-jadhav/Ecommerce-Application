package com.EcomerceApp.inventory_service.kafkaConfig;

import com.EcomerceApp.inventory_service.dto.ProductCreatedEvent;
import com.EcomerceApp.inventory_service.model.Inventory;
import com.EcomerceApp.inventory_service.repo.InventoryRepository;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@KafkaListener(topics = "product-created-event")
public class ProductEventConsumer {

    private final InventoryRepository repository;

    public ProductEventConsumer(InventoryRepository repository) {
        this.repository = repository;
    }

//1. Consumer Failures (Resilience)
//    Example: Inventory DB is down while event is consumed.
//    If we don’t retry → the product will exist in Product Service but not in Inventory.
//            Solution → Add retries + Dead Letter Topic (DLT).

//Annotation (@RetryableTopic): Use if you want per-consumer fine control.
    @RetryableTopic(
            attempts = "3",
            backoff = @Backoff(delay = 2000, multiplier = 2.0),
            dltTopicSuffix = "-dlt"
    )
    @KafkaHandler
    public void consume(ProductCreatedEvent event) {
        try {
            Optional<Inventory> existing = repository.findBySkuCode((event.getSkuCode()));
            if (existing.isEmpty()) {
                Inventory i = new Inventory();
                i.setSkuCode(event.getSkuCode());
                i.setQuantity(event.getQuantity() != null ? event.getQuantity() : 0);

                repository.save(i);
                System.out.println("Inventory created for SKU: " + event.getSkuCode());
            }else {
                System.out.println("⚠️ Duplicate event received. SKU already exists: " + event.getSkuCode());
            }
    } catch(Exception e){
            System.err.println("❌ Failed to save inventory for SKU: " + event.getSkuCode() + " - " + e.getMessage());
            throw e; // triggers retry, then DLT if still fails
        }

    }

}
