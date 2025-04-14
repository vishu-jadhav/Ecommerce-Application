package com.EcomerceApp.product_service.service;

import com.EcomerceApp.product_service.dto.ProductDto;
import com.EcomerceApp.product_service.dto.ProductResponse;
import com.EcomerceApp.product_service.model.Product;

import java.util.List;

public interface ProductService {

    public Product saveProduct(ProductDto productDto);

    ProductResponse getById(Long productId);

    ProductResponse updateProduct(Long id, ProductDto productDto);

    void deleteProduct(Long productId);

    List<ProductResponse> getAllProduct();
}
