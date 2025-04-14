package com.EcomerceApp.inventory_service.service;

import com.EcomerceApp.inventory_service.repo.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{


    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public boolean isInStock(String skuCode) {

        return inventoryRepository.findBySkuCode(skuCode).isPresent();

    }
}
