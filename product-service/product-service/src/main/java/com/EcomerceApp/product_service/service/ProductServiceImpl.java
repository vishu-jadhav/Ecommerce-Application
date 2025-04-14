package com.EcomerceApp.product_service.service;


import com.EcomerceApp.product_service.dto.ProductDto;
import com.EcomerceApp.product_service.dto.ProductResponse;
import com.EcomerceApp.product_service.model.Product;
import com.EcomerceApp.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepo;

    public ProductServiceImpl(ProductRepository productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product saveProduct(ProductDto productDto) {
        Product product=toProduct(productDto);
        return productRepo.save(product);
    }

    @Override
    public ProductResponse getById(Long productId) {
        Product pr= productRepo.findById(productId).orElseThrow(() -> new RuntimeException("Product not Available"));

        return ToProductResponse(pr);

    }

    @Override
    public ProductResponse updateProduct(Long id, ProductDto productDto) {
        Product product=productRepo.findById(id).orElseThrow(() -> new RuntimeException("NOT_FOUND"));

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        productRepo.save(product);

        return ToProductResponse(product);

    }

    @Override
    public void deleteProduct(Long productId) {
        Product product=productRepo.findById(productId).orElseThrow(() -> new RuntimeException("No product found"));

        productRepo.delete(product);
        System.out.println("Product Deleted Successfully");
    }

    @Override
    public List<ProductResponse> getAllProduct() {
        List<Product> pList=productRepo.findAll();
        return pList.stream()
                .map(this::ToProductResponse) // using your private method
                .collect(Collectors.toList());

    }

    private ProductResponse ToProductResponse(Product pr) {
        ProductResponse p=new ProductResponse();
        p.setId(pr.getId());
        p.setName(pr.getName());
        p.setDescription(pr.getDescription());
        p.setPrice(pr.getPrice());

        return p;
    }

    private Product toProduct(ProductDto productDto) {
        Product p=new Product();
        p.setName(productDto.getName());
        p.setDescription(productDto.getDescription());
        p.setPrice(productDto.getPrice());

        return p;
    }


}
