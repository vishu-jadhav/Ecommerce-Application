package com.EcomerceApp.product_service.config;

import com.EcomerceApp.product_service.dto.ProductCreatedEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;


    public ProductEventProducer(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void publishProductCreatedEvent(ProductCreatedEvent productCreatedEvent){
        kafkaTemplate.send("product-created-event", productCreatedEvent.getName(),productCreatedEvent);
    }
}
