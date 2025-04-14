package com.EcomerceApp.order_service.service;

import com.EcomerceApp.order_service.dto.OrderLineItemDto;
import com.EcomerceApp.order_service.dto.OrderRequest;
import com.EcomerceApp.order_service.model.Order;
import com.EcomerceApp.order_service.model.OrderLineItems;
import com.EcomerceApp.order_service.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }


    @Override
    public void placeOreder(OrderRequest orderRequest) {
        Order order=new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList=orderRequest.getOrderLineItemLists().stream()
                .map(this::mapToOrderLine)
                .toList();

        order.setOrderLineItemsList(orderLineItemsList);
        orderRepository.save(order);



    }

    private OrderLineItems mapToOrderLine(OrderLineItemDto orderLineListDto) {

        OrderLineItems o=new OrderLineItems();
        o.setPrice(orderLineListDto.getPrice());
        o.setQuantity(orderLineListDto.getQuantity());
        o.setSkuCode(orderLineListDto.getSkuCode());
        return o;
    }
}
