package com.EcomerceApp.product_service.controller;


import com.EcomerceApp.product_service.dto.ProductDto;
import com.EcomerceApp.product_service.dto.ProductResponse;
import com.EcomerceApp.product_service.model.Product;
import com.EcomerceApp.product_service.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductDto productDto){

        Product saveProduct=productService.saveProduct(productDto);
        System.out.println("Product saved ");

    }

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getById(@PathVariable Long productId){
        return productService.getById(productId);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto){

        return productService.updateProduct(id,productDto);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePoduct(@PathVariable Long productId){

        productService.deleteProduct(productId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct(){
        return productService.getAllProduct();
    }
}
