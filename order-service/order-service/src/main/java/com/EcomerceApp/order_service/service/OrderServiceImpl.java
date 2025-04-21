package com.EcomerceApp.order_service.service;

import com.EcomerceApp.order_service.config.InventoryClient;
import com.EcomerceApp.order_service.dto.InventoryResponse;
import com.EcomerceApp.order_service.dto.OrderLineItemDto;
import com.EcomerceApp.order_service.dto.OrderRequest;
import com.EcomerceApp.order_service.model.Order;
import com.EcomerceApp.order_service.model.OrderLineItems;
import com.EcomerceApp.order_service.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
   // private final RestTemplate restTemplate;


    public OrderServiceImpl(OrderRepository orderRepository,InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient=inventoryClient;
    }


    @Override
    public void placeOreder(OrderRequest orderRequest) {

        //RestTemplate Configuration
//        boolean allProductsInStock=orderRequest.getOrderLineItemLists().stream()
//                .allMatch(item ->{
//                    String url="http://localhost:8082/api/inventory/"+ item.getSkuCode();
//                    Boolean response=restTemplate.getForObject(url,Boolean.class);
//                    return response !=null&&response;
//                });


//        if (!allProductsInStock) {
//            throw new RuntimeException("Product not in stock");
//        }

        List<String> skuCodes = orderRequest.getOrderLineItemLists().stream()
                .map(OrderLineItemDto::getSkuCode)
                .toList();

        // 🔁 Using Feign client to call inventory service
        List<InventoryResponse> inventoryResponses = inventoryClient.isInStock(skuCodes);

        boolean allProductsInStock = inventoryResponses.stream()
                .allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new RuntimeException("Product not in stock");
        }
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
