package com.paytonkawa.product_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.paytonkawa.product_service.entity.Product;
import com.paytonkawa.product_service.repo.ProductRepo;
import com.paytonkawa.product_service.services.ProductServices;

@SpringBootTest
class ProductServiceApplicationTests {

	 @Autowired
	    private ProductRepo productRepo;

	    private ProductServices productServices;

	    
	    @Test
	    void testMainMethod() {
	        ProductServiceApplication.main(new String[]{}); 
	    }
	    
	    @BeforeEach
	    void setUp() {
	        this.productServices = new ProductServices(productRepo);
	        productRepo.deleteAll();
	    }

	    @Test
	    void testCreateProduct() {
	        Product product = new Product("Product1", "Description1", 10, 99.99);
	        ResponseEntity<Map<String, String>> response = productServices.createProduct(product);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertTrue(response.getBody().containsKey("message"));
	        assertEquals("productProduct1 saved succefully", response.getBody().get("message"));

	        Optional<Product> savedProduct = productRepo.findById(product.getId());
	        assertTrue(savedProduct.isPresent());
	        assertEquals("Product1", savedProduct.get().getName());
	    }

	    @Test
	    void testUpdateProduct() {
	        Product product = new Product("Product1", "Description1", 10, 99.99);
	        productRepo.save(product);

	        Product updatedProduct = new Product("UpdatedName", null, 0, 89.99);
	        ResponseEntity<Map<String, String>> response = productServices.updateProduct(product.getId(), updatedProduct);

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("product with product name UpdatedName updated succefully", response.getBody().get("message"));

	        Product foundProduct = productRepo.findById(product.getId()).get();
	        assertEquals("UpdatedName", foundProduct.getName());
	        assertEquals(89.99, foundProduct.getPrice());
	        assertEquals(10, foundProduct.getStock()); // Stock should not change since it was set to 0 in the update
	    }

	    @Test
	    void testDeleteProduct() {
	        Product product = new Product("Product1", "Description1", 10, 99.99);
	        productRepo.save(product);

	        ResponseEntity<Map<String, String>> response = productServices.deleteProduct(product.getId());

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertEquals("product with product name Product1 deleted succefully", response.getBody().get("message"));

	        Optional<Product> deletedProduct = productRepo.findById(product.getId());
	        assertTrue(deletedProduct.isEmpty());
	    }

	    @Test
	    void testProductById() {
	        Product product = new Product("Product1", "Description1", 10, 99.99);
	        productRepo.save(product);

	        ResponseEntity<Product> response = productServices.productById(product.getId());

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	        assertEquals("Product1", response.getBody().getName());
	    }

	    @Test
	    void testGetAllProducts() {
	        Product product1 = new Product("Product1", "Description1", 10, 99.99);
	        Product product2 = new Product("Product2", "Description2", 20, 199.99);

	        productRepo.save(product1);
	        productRepo.save(product2);

	        ResponseEntity<List<Product>> response = productServices.getAllProducts();

	        assertEquals(HttpStatus.OK, response.getStatusCode());
	        assertNotNull(response.getBody());
	        System.out.println("/////////////////////////////////////=>"+response.getBody());
	        assertEquals(2, response.getBody().size());
	    }

}
