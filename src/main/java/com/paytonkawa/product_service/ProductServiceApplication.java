package com.paytonkawa.product_service;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.paytonkawa.product_service.entity.Product;
import com.paytonkawa.product_service.repo.ProductRepo;

@SpringBootApplication
public class ProductServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
    CommandLineRunner start(ProductRepo repo) {
		return args->{
			Product product1 = new Product("testProduct1","product test description for product 1",45,10);
			Product product2 = new Product("testProduct","product test description for product 2",30,8);
			repo.saveAll(List.of(product1,product2));
		};
	}
}
