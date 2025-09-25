package com.EcomerceApp.inventory_service.service;


import com.EcomerceApp.inventory_service.dto.InventoryResponse;
import com.EcomerceApp.inventory_service.dto.InventoryStatus;
import com.EcomerceApp.inventory_service.model.Inventory;
import com.EcomerceApp.inventory_service.repo.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional
public class InventoryServiceImpl implements InventoryService{


    private final InventoryRepository inventoryRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<InventoryResponse> isInStock(List<String> skuCodes) {

        // Fetch all inventory records for given SKU codes
        List<Inventory> inventories = inventoryRepository.findBySkuCodeIn(skuCodes);

        // Convert list to a map for quick lookup
        Map<String, Inventory> inventoryMap = inventories.stream()
                .collect(Collectors.toMap(Inventory::getSkuCode, inv -> inv));

        // Build response for all requested SKUs
        return skuCodes.stream()
                .map(sku -> {
                    InventoryResponse response = new InventoryResponse();
                    response.setSkuCode(sku);

                    Inventory inventory = inventoryMap.get(sku);

                    if (inventory == null) {
                        response.setStatus(InventoryStatus.NOT_FOUND);
                        response.setAvailableQty(0);
                    } else if (inventory.getQuantity() > 0) {
                        response.setStatus(InventoryStatus.AVAILABLE);
                        response.setAvailableQty(inventory.getQuantity());
                    } else {
                        response.setStatus(InventoryStatus.OUT_OF_STOCK);
                        response.setAvailableQty(0);
                    }

                    return response;
                })
                .toList();
    }
}
