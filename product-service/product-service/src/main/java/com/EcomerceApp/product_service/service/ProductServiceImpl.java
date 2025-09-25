package com.EcomerceApp.product_service.service;


import com.EcomerceApp.product_service.config.ProductEventProducer;
import com.EcomerceApp.product_service.dto.ProductCreatedEvent;
import com.EcomerceApp.product_service.dto.ProductDto;
import com.EcomerceApp.product_service.dto.ProductResponse;
import com.EcomerceApp.product_service.exception.ProductNotFoundException;
import com.EcomerceApp.product_service.model.Product;
import com.EcomerceApp.product_service.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ObjectMapper mapper;
    private final ProductRepository productRepo;
    private final  ProductEventProducer producer;

    public ProductServiceImpl(ObjectMapper mapper, ProductRepository productRepo, ProductEventProducer producer) {
        this.mapper = mapper;
        this.productRepo = productRepo;
        this.producer = producer;
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        //Product product=toProduct(productDto);

        Product product= mapper.convertValue(productDto, Product.class);    //used object mapper class to convert data or for data transfer from dto to entity
        //ObjectMapper is a class from Jackson library (com.fasterxml.jackson.databind.ObjectMapper).
        //It helps us convert Java objects to JSON and back or even map between two Java objects.
//        For your E-commerce microservices:
//        Use ModelMapper (or MapStruct if you want compile-time mapping) for DTO ↔ Entity conversions.
//        Use ObjectMapper only for JSON → Object or Object → JSON operations.

        // Generate SKU code (can be any unique pattern)
        product.setSkuCode(generateSkuCode(product.getName()));

        Product savedProduct=productRepo.save( product);


        //produce event for Product created event

        ProductCreatedEvent event= mapper.convertValue(product,ProductCreatedEvent.class);
        producer.publishProductCreatedEvent(event);


        return savedProduct;
    }

    @Override
    public ProductResponse getById(Long productId) {
        Product pr= productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not Available"));

        return mapper.convertValue(pr,ProductResponse.class);

    }

    @Override
    public ProductResponse updateProduct(Long id, ProductDto productDto) {
        Product product=productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("NOT_FOUND"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        productRepo.save(product);

        return mapper.convertValue(product,ProductResponse.class);

    }

    @Override
    public void deleteProduct(Long productId) {
        Product product=productRepo.findById(productId).orElseThrow(() -> new ProductNotFoundException("No product found"));

        productRepo.delete(product);
        System.out.println("Product Deleted Successfully");
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> pList=productRepo.findAll();
        return pList.stream()
                .map(product -> mapper.convertValue(product,ProductResponse.class)) // using your private method
                .collect(Collectors.toList());

    }

    private String generateSkuCode(String productName) {
        String randomId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return productName.replaceAll("\\s+", "-").toUpperCase() + "-" + randomId;
    }

//    private ProductResponse ToProductResponse(Product pr) {
//        ProductResponse p=new ProductResponse();
//        p.setId(pr.getId());
//        p.setName(pr.getName());
//        p.setDescription(pr.getDescription());
//        p.setPrice(pr.getPrice());
//
//        return p;
//    }
//
//    private Product toProduct(ProductDto productDto) {
//        Product p=new Product();
//        p.setName(productDto.getName());
//        p.setDescription(productDto.getDescription());
//        p.setPrice(productDto.getPrice());
//
//        return p;
//    }


}
