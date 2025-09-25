package com.EcomerceApp.inventory_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "failed_events")
public class FailedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String key;
    @Column(columnDefinition = "TEXT")
    private String payload;
    private String reason;
    private LocalDateTime failedAt;

    public FailedEvent() {
    }

    public FailedEvent(String topic, String key, String payload, String reason) {
        this.topic = topic;
        this.key = key;
        this.payload = payload;
        this.reason = reason;
        this.failedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FailedEvent that = (FailedEvent) o;
        return Objects.equals(id, that.id) && Objects.equals(topic, that.topic) && Objects.equals(key, that.key) && Objects.equals(payload, that.payload) && Objects.equals(reason, that.reason) && Objects.equals(failedAt, that.failedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, topic, key, payload, reason, failedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDateTime getFailedAt() {
        return failedAt;
    }

    public void setFailedAt(LocalDateTime failedAt) {
        this.failedAt = failedAt;
    }
}
