package com.EcomerceApp.order_service.model;


import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="t-order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)  //One Order can have many OrderLineItems.
    /*This means that any operation performed on the parent entity (Order) will be cascaded to the child entities (OrderLineItems).
    For example:
    If you save an Order, all its OrderLineItems will also be saved automatically.
    If you delete an Order, all its associated OrderLineItems will also be deleted.
    */
    private List<OrderLineItems> orderLineItemsList;

    public Order() {
    }

    public Order(Long id, String orderNumber, List<OrderLineItems> orderLineItemsList) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderLineItemsList = orderLineItemsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderLineItems> getOrderLineItemsList() {
        return orderLineItemsList;
    }

    public void setOrderLineItemsList(List<OrderLineItems> orderLineItemsList) {
        this.orderLineItemsList = orderLineItemsList;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", orderLineItemsList=" + orderLineItemsList +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) && Objects.equals(orderNumber, order.orderNumber) && Objects.equals(orderLineItemsList, order.orderLineItemsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber, orderLineItemsList);
    }
}
