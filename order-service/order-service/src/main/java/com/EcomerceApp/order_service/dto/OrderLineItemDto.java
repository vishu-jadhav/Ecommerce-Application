package com.EcomerceApp.order_service.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderLineItemDto {

    private Long id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;


    public OrderLineItemDto() {
    }

    public OrderLineItemDto(Long id, String skuCode, BigDecimal price, Integer quantity) {
        this.id = id;
        this.skuCode = skuCode;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setOrderNumber(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderLineItemList{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderLineItemDto that = (OrderLineItemDto) o;
        return Objects.equals(id, that.id) && Objects.equals(skuCode, that.skuCode) && Objects.equals(price, that.price) && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, skuCode, price, quantity);
    }
}
