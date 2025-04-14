package com.EcomerceApp.order_service.dto;


import java.util.List;
import java.util.Objects;

public class OrderRequest {
    private List<OrderLineItemDto> orderLineItemLists;


    public OrderRequest() {
    }

    public OrderRequest(List<OrderLineItemDto> orderLineItemLists) {
        this.orderLineItemLists = orderLineItemLists;
    }

    public List<OrderLineItemDto> getOrderLineItemLists() {
        return orderLineItemLists;
    }

    public void setOrderLineItemLists(List<OrderLineItemDto> orderLineItemLists) {
        this.orderLineItemLists = orderLineItemLists;
    }

    @Override
    public String toString() {
        return "OrderRequest{" +
                "orderLineItemLists=" + orderLineItemLists +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return Objects.equals(orderLineItemLists, that.orderLineItemLists);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(orderLineItemLists);
    }
}
