package com.EcomerceApp.product_service;

import com.EcomerceApp.product_service.dto.ProductDto;
import com.EcomerceApp.product_service.dto.ProductResponse;
import com.EcomerceApp.product_service.model.Product;
import com.EcomerceApp.product_service.repository.ProductRepository;
import com.EcomerceApp.product_service.service.ProductService;
import com.EcomerceApp.product_service.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductServiceImpl productService;

	private Product product;
	private ProductDto productDto;

	@BeforeEach
	void setUp(){

		productDto = new ProductDto();
		productDto.setName("Laptop");
		productDto.setDescription("Gaming Laptop");
		productDto.setPrice(new BigDecimal("1000.0"));

		product = new Product();
		product.setId(1L);
		product.setName("Laptop");
		product.setDescription("Gaming Laptop");
		product.setPrice(new BigDecimal("1000.0"));
	}

	@Test
	public void TestCreateProduct(){

		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);

		//when
		Product response=productService.saveProduct(productDto);

		//then
		assertEquals(product.getName(),response.getName());
		assertEquals(product.getDescription(),response.getDescription());
		assertEquals(product.getPrice(),response.getPrice());

	}


	@Test
	public void Test_GetProductById(){

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		ProductResponse response=productService.getById(1L);

		assertNotNull(response);
		assertEquals(product.getName(),response.getName());
		assertEquals(product.getDescription(),response.getDescription());
		assertEquals(product.getPrice(),response.getPrice());

	}


	@Test
	public void getProductById_throwException_whenNoProduct(){

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> productService.getById(1L));
	}

	@Test
	public void Test_updateProduct(){

		Mockito.when(productRepository.save(Mockito.any(Product.class))).thenReturn(product);
		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		ProductDto updatedProd=new ProductDto();
		updatedProd.setName("Updated Laptop");
		updatedProd.setDescription("Updated Gaming Laptop");
		updatedProd.setPrice(new BigDecimal("99999"));

		ProductResponse response=productService.updateProduct(1L,updatedProd);

		assertNotNull(response);
		assertEquals("Updated Laptop",response.getName());
		assertEquals("Updated Gaming Laptop",response.getDescription());
		assertEquals(new BigDecimal("99999"),response.getPrice());

	}


	@Test
	public void Test_delete_product(){

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		productService.deleteProduct(1l);

		verify(productRepository, times(1)).delete(product);
	}

	@Test
	public void ThrowException_WhenNoProduct(){

		Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, ()->productService.deleteProduct(1L));
	}


	@Test
	public void TestGetAllProduct(){

		List<Product> productList= Collections.singletonList(product);
		Mockito.when(productRepository.findAll()).thenReturn(productList);

		List<ProductResponse> response=productService.getAllProduct();

		assertEquals(1, response.size());
		assertEquals("Laptop" , response.get(0).getName());
		assertEquals("Gaming Laptop" , response.get(0).getDescription());
		assertEquals(new BigDecimal("1000.0") , response.get(0).getPrice());
	}





}

