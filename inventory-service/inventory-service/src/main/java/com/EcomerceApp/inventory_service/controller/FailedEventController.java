package com.EcomerceApp.inventory_service.controller;

import com.EcomerceApp.inventory_service.model.FailedEvent;
import com.EcomerceApp.inventory_service.repo.FailedEventRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/failed-events")
public class FailedEventController {

    private final FailedEventRepository failedEventRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public FailedEventController(FailedEventRepository failedEventRepository, KafkaTemplate<String, String> kafkaTemplate) {
        this.failedEventRepository = failedEventRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

//    Fetch all failed events stored in DB.
//    Used by monitoring tools / dashboards / ops team to inspect failures.
    @GetMapping
    public List<FailedEvent> getAllFailedEvents(){
        return failedEventRepository.findAll();
    }


//    Fetch failed event by ID
//    If not found → throw error.
//    This ensures only valid stored failures can be retried.
//            Re-publish to Kafka
//    Uses KafkaTemplate.send() to send the stored payload back to original topic (product-created-event).
//    This re-triggers normal processing (like a fresh event).
//    Delete the record
//    Removes the failed event from DB after retry.
//    Prevents duplicate retries for the same event.
//            Return response
//    Confirmation message "✅ Retried event with ID X".

    @PostMapping("/{id}/retry")
    public String retryEvent(@PathVariable Long id){

        FailedEvent failedEvent=failedEventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Failed event not found "));

        kafkaTemplate.send("product-created-event",failedEvent.getPayload());

        failedEventRepository.deleteById(id);

        return "✅ Retried event with ID " + id;
    }
}


//📌 Real-World Usage Flow

//Message fails in Kafka → moves to DLT → consumed and stored in failed_events table.
//Ops team checks via UI/Swagger/Postman:
//GET /api/failed-events → see all failures.
//Ops fixes root cause (e.g., bad DB config, missing SKU).
//Ops retries the event:
//POST /api/failed-events/{id}/retry
//Event is re-published to Kafka, processed normally, and removed from DB.