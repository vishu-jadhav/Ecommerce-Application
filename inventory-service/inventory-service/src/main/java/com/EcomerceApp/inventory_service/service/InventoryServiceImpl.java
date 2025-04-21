package com.EcomerceApp.inventory_service.service;

import com.EcomerceApp.inventory_service.dto.InventoryResponse;
import com.EcomerceApp.inventory_service.model.Inventory;
import com.EcomerceApp.inventory_service.repo.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{


    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<InventoryResponse> isInStock(List<String> skuCode) {

        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory -> {
                    InventoryResponse response = new InventoryResponse();
                    response.setSkuCode(inventory.getSkuCode());
                    response.setInStock(inventory.getQuantity() > 0);
                    return response;
                })
//                .map(inventory ->
//                        InventoryResponse.builder()
//                                .skuCode(inventory.getSkuCode())
//                                .isInStock(inventory.getQuantity() > 0)
//                                .build()
//                )
                .toList();

    }
}
