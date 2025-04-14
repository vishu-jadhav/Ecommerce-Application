package com.EcomerceApp.order_service.service;

import com.EcomerceApp.order_service.dto.OrderRequest;
import com.EcomerceApp.order_service.model.Order;

public interface OrderService {

    public void placeOreder(OrderRequest orderRequest);
}
