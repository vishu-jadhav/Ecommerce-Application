package com.EcomerceApp.inventory_service.kafkaConfig;

import com.EcomerceApp.inventory_service.dto.ProductCreatedEvent;
import com.EcomerceApp.inventory_service.model.FailedEvent;
import com.EcomerceApp.inventory_service.repo.FailedEventRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ProductEventDLTConsumer {

    private FailedEventRepository failedEventRepository;

    public ProductEventDLTConsumer(FailedEventRepository failedEventRepository) {
        this.failedEventRepository = failedEventRepository;
    }

    @KafkaListener(topics = "product-created-topic-dlt", groupId = "inventory-service-dlt")
    public void consumeDLT(ConsumerRecord<String, ProductCreatedEvent> record){

        ProductCreatedEvent event=record.value();

        FailedEvent failedEvent=new FailedEvent(
                record.topic(),
                record.key(),
                record.toString(),
                "processing failed after retries"
        );

        failedEventRepository.save(failedEvent);


        System.err.println("moved to DLT : "+ event.getSkuCode()+"reason :  Processing failed after retries ");

        // 1. Log into monitoring system (ELK, Prometheus)
        // 2. Send an alert/email/slack message
        // 3. Optionally move it into a DB table for manual fixing
    }
}
//🔄 Flow with DLT in Action
//ProductService → sends ProductCreatedEvent.
//InventoryService → tries to save.
//If DB is down, it retries (3 times).
//If still fails → message is sent to product-created-event-dlt.
//        ProductEventDLTConsumer → picks up the event and logs/alerts it.
//DevOps/Support team checks logs, fixes root cause (e.g., DB outage), and may replay the DLT messages back into the main topic.
