package com.paytonkawa.product_service.controller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.paytonkawa.product_service.entity.Product;
import com.paytonkawa.product_service.services.ProductServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("product")
public class ProductController {

	@Autowired
	private ProductServices productServices;
	
	@Operation(summary = "get  a product with id", description = "get a product with the provided id if it exist in the database")
	@ApiResponse(responseCode = "200", description = "the product if found")
	@GetMapping("{id}")
	public ResponseEntity<Product> getProductById(@PathVariable int id){
		return this.productServices.productById(id);
	}
	 
	@Operation(summary = "Create a new product", description = "Create a new product with the provided details")
	 @ApiResponse(responseCode = "200", description = "product saved successfully")
	@PostMapping()
	public ResponseEntity<Map<String, String>> createProduct(@Valid @RequestBody Product product){
		return this.productServices.createProduct(product);
	}
	
    @Operation(summary = "update a product", description = "update a product the provided id  and details if it exist in the database")
	@PutMapping("{id}")
	public ResponseEntity<Map<String,String>> updateCustomer(@PathVariable int id,@RequestBody Product productToUpdate){
		return this.productServices.updateProduct(id, productToUpdate);
	}
    
    @Operation(summary = "delete a product", description = "update a product the provided id  and details if it exist in the database")
	@DeleteMapping("{id}")
	public ResponseEntity<Map<String,String>> deleteCustomer(@PathVariable int id){
		return this.productServices.deleteProduct(id);
	}
    
    @Operation(summary = "get all products", description = "list all the products available ")
  	@GetMapping()
    public ResponseEntity<List<Product>> getAllProducts(){
    	return this.productServices.getAllProducts();
    }
}
