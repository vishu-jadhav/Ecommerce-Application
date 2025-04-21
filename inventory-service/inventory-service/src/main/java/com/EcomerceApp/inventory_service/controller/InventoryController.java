package com.EcomerceApp.inventory_service.controller;


import com.EcomerceApp.inventory_service.dto.InventoryResponse;
import com.EcomerceApp.inventory_service.model.Inventory;
import com.EcomerceApp.inventory_service.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode){

        return  inventoryService.isInStock(skuCode);
    }
}
