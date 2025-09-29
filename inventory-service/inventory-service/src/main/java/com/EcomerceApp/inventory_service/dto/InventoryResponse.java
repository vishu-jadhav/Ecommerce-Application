package com.EcomerceApp.inventory_service.dto;

import lombok.Builder;

import java.util.Objects;

//@Builder
public class InventoryResponse {

    private String skuCode;
    private InventoryStatus status;
    private int availableQty;

    // Getters & Setters

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public InventoryStatus getStatus() {
        return status;
    }

    public void setStatus(InventoryStatus status) {
        this.status = status;
    }

    public int getAvailableQty() {
        return availableQty;
    }

    public void setAvailableQty(int availableQty) {
        this.availableQty = availableQty;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InventoryResponse that = (InventoryResponse) o;
        return availableQty == that.availableQty && Objects.equals(skuCode, that.skuCode) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuCode, status, availableQty);
    }

    @Override
    public String toString() {
        return "InventoryResponse{" +
                "skuCode='" + skuCode + '\'' +
                ", status=" + status +
                ", availableQty=" + availableQty +
                '}';
    }
}
