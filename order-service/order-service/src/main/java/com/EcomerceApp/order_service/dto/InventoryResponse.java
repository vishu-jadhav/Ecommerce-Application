package com.EcomerceApp.order_service.dto;

import java.util.Objects;

public class InventoryResponse {

    private String skuCode;
    private boolean isInStock;

    public InventoryResponse() {
    }

    public InventoryResponse(String skuCode, boolean isInStock) {
        this.skuCode = skuCode;
        this.isInStock = isInStock;
    }

    @Override
    public String toString() {
        return "InventoryResponse{" +
                "skuCode='" + skuCode + '\'' +
                ", isInStock=" + isInStock +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InventoryResponse that = (InventoryResponse) o;
        return isInStock == that.isInStock && Objects.equals(skuCode, that.skuCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skuCode, isInStock);
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public boolean isInStock() {
        return isInStock;
    }

    public void setInStock(boolean inStock) {
        isInStock = inStock;
    }
}
