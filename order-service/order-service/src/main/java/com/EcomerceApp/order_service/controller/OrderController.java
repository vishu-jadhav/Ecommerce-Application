package com.EcomerceApp.order_service.controller;

import com.EcomerceApp.order_service.dto.OrderRequest;
import com.EcomerceApp.order_service.service.OrderService;
import com.EcomerceApp.order_service.service.OrderServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOreder(orderRequest);
        return "Order placed Succefully";
    }
}
