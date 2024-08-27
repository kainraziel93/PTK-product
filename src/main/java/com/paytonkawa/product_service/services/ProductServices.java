package com.paytonkawa.product_service.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.paytonkawa.product_service.dto.UpdateProductStockDto;
import com.paytonkawa.product_service.entity.Product;
import com.paytonkawa.product_service.repo.ProductRepo;

import java.util.List;

@Service
public class ProductServices {

	@Autowired
	private ProductRepo productRepo;
	public ResponseEntity<Map<String, String>> createProduct (Product product){
		try {
			this.productRepo.save(product);
			return ResponseEntity.ok(Map.of("message","product"+product.getName()+" saved succefully"));
		} catch (Exception e) {
			return  ResponseEntity.badRequest().build();
		}
	}
	
	public ResponseEntity<Map<String, String>> updateProduct(int productId,Product product){
			try {
				Product productToUpdate = this.productRepo.findById(productId).get();
				if(product.getName()!=null) {
					productToUpdate.setName(product.getName());
				}
				if(product.getDescription()!=null) {
					productToUpdate.setDescription(product.getDescription());
				}
				if(product.getPrice()!=0) {
					productToUpdate.setPrice(product.getPrice());
				}
				if(product.getStock()!=0) {
					productToUpdate.setStock(product.getStock());
				}
				this.productRepo.save(productToUpdate);
				return ResponseEntity.ok(Map.of("message","product with product name "+product.getName()+" updated succefully"));
				
			} catch (Exception e) {
				System.out.println("error => "+e.getMessage());
				return  ResponseEntity.badRequest().build();
			}	
	}
	
	public ResponseEntity<Map<String,String>> deleteProduct(int productId){
		try {
			Product product = this.productRepo.findById(productId).get();
			this.productRepo.delete(product);
			return ResponseEntity.ok(Map.of("message","product with product name "+product.getName()+" deleted succefully"));
			
		} catch (Exception e) {
			System.out.println("error => "+e.getMessage());
			return  ResponseEntity.badRequest().build();
		}
	}
	
	public ResponseEntity<Product> productById(int productId){
		try {
			Product product = this.productRepo.findById(productId).get();
			return ResponseEntity.ok(product);
			
		} catch (Exception e) {
			System.out.println("error => "+e.getMessage());
			return  ResponseEntity.badRequest().build();
		}
	}
	
	public ResponseEntity<List<Product>> getAllProducts(){
		try {
			List<Product> products = this.productRepo.findAll();
			return ResponseEntity.ok(products);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	@KafkaListener(topics="update_stock",groupId="1",containerFactory = "kafkaListnerContainerFactory")
	public void updateStock(UpdateProductStockDto updateProductMessage) {
		System.out.println("updating product  with the =>"+updateProductMessage.getProductId()+" stock removing  a quantity of=>"+updateProductMessage.getQuantity()+" ....");
		Product product = productRepo.findById(updateProductMessage.getProductId()).get();
		product.setStock(product.getStock()-updateProductMessage.getQuantity());
	}
}
