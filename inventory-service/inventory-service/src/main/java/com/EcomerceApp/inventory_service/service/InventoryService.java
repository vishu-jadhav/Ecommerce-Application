package com.EcomerceApp.inventory_service.service;

import com.EcomerceApp.inventory_service.dto.InventoryResponse;
import com.EcomerceApp.inventory_service.model.Inventory;

import java.util.List;

public interface InventoryService {

    public List<InventoryResponse> isInStock(List<String> skuCode);
}
